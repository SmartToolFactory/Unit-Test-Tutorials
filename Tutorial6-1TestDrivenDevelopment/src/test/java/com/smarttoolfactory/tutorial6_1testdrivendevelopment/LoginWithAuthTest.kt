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
'  * When  describes the action that the actor in the system or stakeholder performs
'  * Then describes the expected outcome of that action
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
      🔥 Test 1: Create a failing test for empty fields
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
        every { authRepository.login(or(any(), ""), or(any(), "")) }

        // When
        val expected = authUseCase.login("", "", false)

        // Then
        verify(exactly = 0) { authRepository.login(or(any(), ""), or(any(), "")) }
        expected assertThatEquals EMPTY_FIELD_ERROR

    }

    /*
        🔥 STEP 4: Test if password length is between 6 and 10 chars
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
        every {
            authRepository.login(any(), password)
        }

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
       🔥 STEP 4: Test if password matches correct expression
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
        every {
            authRepository.login(userName, any())
        }

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
        🔥 STEP 6: Test invalid authentication state
     */
    @Test
    fun `Wrong password or user name fails authentication`() {

        // Given
        val userName = "user@example.com"
        val password = "password"

        every {
            authRepository.login(userName, password)
        } returns null

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
        🔥 STEP 8: Test if user name matches correct expression
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

        every {
            accountManager.saveToken(any())
        }


        // When
        val expected = authUseCase.login(userName, password)

        // Then
        expected assertThatEquals SUCCESSFUL_AUTHENTICATION
        verify(exactly = 1) { authRepository.login(userName, password) }
        confirmVerified(authRepository)
    }


    /*
       🔥 STEP 10: Test if number of login attempts exceed threshold
    */
    @Test
    fun `Number of login attempts exceed maximum number`() {

        // Given
        val userName = "user@example.com"
        val password = "password"

        every {
            authRepository.login(userName, password)
        } returns null

        every {
            accountManager.saveToken(any())
        } returns false

        // When
        authUseCase.login(userName, password)
        authUseCase.login(userName, password)
        val expected = authUseCase.login(userName, password)

        expected assertThatEquals MAX_NUMBER_OF_ATTEMPTS_ERROR

        verify(exactly = 3) { authRepository.login(userName, password) }
        // 🤨 Should i call this here
        verify(exactly = 0) { accountManager.saveToken(any()) }

    }


    /*
       🔥 STEP 12: Test if token is saved
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

        verify(exactly = 2) { authRepository.login(userName, password) }
        verify(exactly = 1) { accountManager.saveToken(token) }


    }

    /*
       🔥 STEP 14: Test if remember me works
    */
    @Test
    fun `Get saved token`() {

        // Given
        val token = "token"

        every {
            accountManager.getToken()
        } returns token

        // When
        val expected = authUseCase.getToken()

        // Then
        assertEquals(expected, token)

    }

    /*
     🔥 STEP 14: Test if remember me works
  */
    @Test
    fun `Logs out successfully`() {

        // Given
        every {
            accountManager.getToken()
        } returns null

        every { accountManager.deleteToken() } just Runs

        // When
        authUseCase.logOut()
        val expected = authUseCase.getToken()

        // Then
        assert(expected == null)
    }
}