package com.smarttoolfactory.tutorial6_1testdrivendevelopment

import com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth.AccountManager
import com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth.AuthRepository
import com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth.AuthResponse
import com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth.AuthUseCase
import com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth.AuthenticationState.*
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 *
 * * Given describes the initial context for the example
 * * When  describes the action that the actor in the system or stakeholder performs
 * * Then describes the expected outcome of that action
 */

/**
 * Authentication test for testing login/authentication  scenarios.
 * As a user i want to be authenticated when i enter correct user name and password.
 *
 * Feature: Login Function
 * To proceed to next page
 * User must be able to login into system, let system remember it's account,
 * and should have maximum 3 attempts before getting it's account locked
 *
 * User can also select remember and next time the app opens it skips logging process
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginWithAuthTest {


    private val authRepository: AuthRepository = mockk()

    private val accountManager: AccountManager = mockk()

    private lateinit var authUseCase: AuthUseCase

    @BeforeEach
    fun setUp() {
        clearMocks(authRepository)
        clearMocks(accountManager)
        authUseCase = AuthUseCase(authRepository, accountManager)
    }

    /*
      🔥 Test 1-A: Create a test for empty fields
     */
    /**
     *  Scenario: Login check with empty fields:
     * * Given I am on the login page
     * * When I enter empty username
     *   And I enter empty password
     *   And I click on the "Login" button
     * * Then I get empty fields error.
     */
    @Test
    fun `Empty fields result empty fields error`() {

        // Given
        every { authRepository.login(or(any(), ""), or(any(), "")) } returns null

        // When
        val expected = authUseCase.login("", "", false)

        // Then
        verify(exactly = 0) { authRepository.login(or(any(), ""), or(any(), "")) }
        expected assertThatEquals EMPTY_FIELD_ERROR

    }

    /*
        🔥 STEP 2-A: Test if password length is between 6 and 10 chars
     */
    /**
     *  Scenario: Login check with empty fields:
     * * Given I am on the login page
     * * When I enter a username less than 6 or more than 10 chars
     *   And I enter password less than 6 or more than 10 chars
     *   And I click on the "Login" button
     * * Then I get fields with invalid field error.
     */
    @ParameterizedTest
    @ValueSource(strings = ["abcde", "abcdabcdefg"])
    fun `Password with less than 6 or more than 10 chars returns invalid fields error`(password: String) {


        // Given
        every { authRepository.login(any(), password) } returns null

        // When
        val expected1 = authUseCase.login("username", password)
        val expected2 = authUseCase.login("username@example.com", password)

        // Then
        expected1 assertThatEquals INVALID_FIELD_ERROR
        expected2 assertThatEquals INVALID_FIELD_ERROR

        verify(exactly = 0) { authRepository.login(any(), any()) }
        confirmVerified(authRepository)
    }


    /*
       🔥 STEP 3-A: Test if user name matches correct type and passes min length
    */

    /**
     *  Scenario: Login check with empty fields:
     * * Given I am on the login page
     * * When I enter a username with incorrect type
     *   And I enter any password
     *   And I click on the "Login" button
     * * Then I get fields with invalid field error.
     */
    @ParameterizedTest
    @ValueSource(strings = ["x@example.com", "test@invalidtest.com"])
    fun `User name with invalid type returns invalid fields error`(userName: String) {

        // Given
        every { authRepository.login(userName, any()) } returns null

        // When
        val expected1 = authUseCase.login(userName, "pass")
        val expected2 = authUseCase.login(userName, "password")

        // Then
        expected1 assertThatEquals INVALID_FIELD_ERROR
        expected2 assertThatEquals INVALID_FIELD_ERROR

        verify(exactly = 0) { authRepository.login(any(), any()) }
        confirmVerified(authRepository)
    }


    /*
        🔥 STEP 4-A: Test invalid authentication via not matching username and/or password
     */
    @Test
    fun `Wrong password or user name fails authentication`() {

        // Given
        val userName = "user@example.com"
        val password = "password"

        every { authRepository.login(userName, password) } returns null

        // When
        val expected = authUseCase.login(userName, password)

        // Then
        expected assertThatEquals FAILED_AUTHENTICATION

        verify(exactly = 1) { authRepository.login(userName, password) }
        verify(exactly = 0) { accountManager.saveToken(any()) }
        confirmVerified(authRepository)
        confirmVerified(accountManager)
    }


    /*
        🔥 STEP 4-C: Test if user name matches correct expression
     */
    @Test
    fun `Correct password and user name passes authentication`() {

        // Given
        val userName = "user@example.com"
        val password = "password"
        val token = "token"

        every {
            authRepository.login(userName, password)
        } returns AuthResponse(true, token)

        every { accountManager.saveToken(any()) }


        // When
        val expected = authUseCase.login(userName, password)

        // Then
        expected assertThatEquals SUCCESSFUL_AUTHENTICATION
        verify(exactly = 1) { authRepository.login(userName, password) }
        confirmVerified(authRepository)
    }


    /*
       🔥 STEP 5-A: Test if number of login attempts exceed threshold
    */
    @Test
    fun `Number of login attempts exceeds maximum number`() {

        // Given
        val userName = "user@example.com"
        val password = "password"

        every { authRepository.login(userName, password) } returns null

        every { accountManager.saveToken(any()) } returns false

        // When
        authUseCase.login(userName, password)
        authUseCase.login(userName, password)
        val expected = authUseCase.login(userName, password)

        expected assertThatEquals MAX_NUMBER_OF_ATTEMPTS_ERROR

        verify(exactly = 3) { authRepository.login(userName, password) }
        verify(exactly = 0) { accountManager.saveToken(any()) }

    }


    /*
       🔥 STEP 6-A: Test if token is saved
    */
    @Test
    fun `Token is saved when remember me is selected`() {

        // Given
        val userName = "user@example.com"
        val password = "password"
        val token = "token"

        every {
            authRepository.login(userName, password)
        } returns AuthResponse(true, token)

        every {
            accountManager.saveToken(token)
        } returns false andThen true

        // When
        //🔥 First login with remember me is not selected and then login with remember me selected
        authUseCase.login(userName, password, false)
        authUseCase.login(userName, password, true)

        // Then
        verify(exactly = 2) { authRepository.login(userName, password) }
        verify(exactly = 1) { accountManager.saveToken(token) }


    }

    /*
       🔥 STEP 7-A: Test if remember me works
    */
    @Test
    fun `Returns saved token successfully`() {

        // Given
        val token = "token"

        every { accountManager.getToken() } returns token

        // When
        val expected = authUseCase.getToken()

        // Then
        assertEquals(expected, token)

    }

    /*
     🔥 STEP 8-A: Test if logs out successfully
  */
    @Test
    fun `Logs out successfully`() {

        // Given
        every { accountManager.getToken() } returns null
        every { accountManager.deleteToken() } just Runs

        // When
        val expected = authUseCase.logOut()
        val token = authUseCase.getToken()

        // Then
        expected assertThatEquals UNAUTHENTICATED
        assert(token == null)
        verify(exactly = 1) { accountManager.deleteToken() }


    }
}