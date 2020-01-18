package com.smarttoolfactory.tutorial6_1testdrivendevelopment

import com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth.AccountManager
import com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth.AuthRepository
import com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth.AuthUseCase
import com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth.AuthenticationState.UNAUTHENTICATED
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

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
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginWithAuthTest {


    private val authRepository: AuthRepository = mockk()

    private val userManager: AccountManager = mockk()

    private lateinit var authUseCase: AuthUseCase

    @BeforeEach
    fun setUp() {
        clearMocks(authRepository)
        clearMocks(userManager)
        authUseCase = AuthUseCase(authRepository, userManager)
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


        // When
        val expected = authUseCase.login("", "")

        // Then
        verify(exactly = 0) { authRepository.login(or(any(), ""), or(any(), "")) }
        expected assertEquals UNAUTHENTICATED

    }


}