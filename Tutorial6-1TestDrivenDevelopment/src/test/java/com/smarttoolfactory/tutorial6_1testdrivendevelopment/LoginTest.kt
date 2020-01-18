package com.smarttoolfactory.tutorial6_1testdrivendevelopment

import com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login.LoginRepo
import com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login.LoginState
import com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login.LoginUseCase
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * Basic login test that checks if
 * * credential fields are empty
 * * password has valid length
 * * user name and password is valid
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginTest {

    private val loginRepository: LoginRepo = mockk()

    private lateinit var loginUseCase: LoginUseCase

    @BeforeEach
    fun setUp() {
        clearMocks(loginRepository)
        loginUseCase = LoginUseCase(loginRepository)
    }

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
        every { loginRepository.login(any(), any()) } returns LoginState.CREDENTIALS_ERROR

        // When
        val expected = loginUseCase.login("", "")

        // Then
        verify(exactly = 0) { loginRepository.login(any(), any()) }
        assertEquals(expected, LoginState.EMPTY_FIELD_ERROR)

    }

    /**
     *  Scenario: Login check with valid length username:
     * * Given I am on the login page
     * * When I enter password with length in boundaries
     *   And I enter any user name
     *   And I click on the "Login" button
     * * Then I get a response from REST api
     */
    @ParameterizedTest
    @ValueSource(strings = ["abcdef", "abcdefgh", "abcabcabcd"])
    fun `Password should have length between 6 and 10 characters`(password: String) {

        // Given
        val slot = slot<String>()
        every {
            loginRepository.login(
                any(),
                capture(slot)
            )
        } returns (LoginState.CREDENTIALS_ERROR)

        // When
        val expected = loginUseCase.login("username", password)

        // Then
        verify(exactly = 1) {loginUseCase.login(any(),slot.captured)}

        expected assertEquals LoginState.CREDENTIALS_ERROR

        assertTrue(slot.captured.length in 6..10)
        confirmVerified(loginRepository)

    }

    /**
     *  Scenario: Login check with valid length username:
     * * Given I am on the login page
     * * When I enter valid username
     *   And I enter valid password
     *   And I click on the "Login" button
     * * Then I get ??
     */
    @ParameterizedTest
    @ValueSource(strings = ["abcde", "abcdabcdefg"])
    fun `Password with less than 6 or more than 10 chars fails`(password: String) {

        // Given
        every {
            loginRepository.login(
                any(),
                password
            )
        } returns (LoginState.CREDENTIALS_ERROR)

        // When
        val expected = loginUseCase.login("username", password)

        // Then
        expected assertNotEquals LoginState.SUCCESS

        verify(exactly = 0) { loginRepository.login(any(), any()) }
        confirmVerified(loginRepository)
    }


    /**
     *  Scenario: Login check with valid length username:
     * * Given I am on the login page
     * * When I enter valid username
     *   And I enter valid password
     *   And I click on the "Login" button
     * * Then I proceed to next screen
     */
    @Test
    fun `Correct credentials result success`() {

        // Given
        every { loginRepository.login("username", "password") } returns LoginState.SUCCESS

        // When
        val expected = loginUseCase.login("username", "password")

        // Then
        assertEquals(expected, LoginState.SUCCESS)
        verify(exactly = 1) { loginRepository.login("username", "password") }
        confirmVerified(loginRepository)

        expected assertEquals LoginState.SUCCESS

    }


}


