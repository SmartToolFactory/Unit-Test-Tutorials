package com.smarttoolfactory.tutorial5_1rxjavatests

import com.smarttoolfactory.tutorial5_1rxjavatests.model.login.*
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginTest {

    private val loginRepository: LoginRepository = mockk()

    private lateinit var loginUseCase: LoginUseCase

    @BeforeEach
    fun setUp() {
        clearMocks( loginRepository)
        loginUseCase = LoginUseCase(loginRepository)
    }


    @Test
    fun `Empty fields result empty fields error`() {

        // Given
        every { loginRepository.login(any(), any()) } returns LoginAttemptState.CREDENTIALS_ERROR

        // When
        val expected = loginUseCase.login("", "")

        // Then
        verify(exactly = 0) { loginRepository.login(any(), any()) }
        assertEquals(expected, LoginAttemptState.EMPTY_FIELD_ERROR)

    }

    @ParameterizedTest
    @ValueSource(strings = ["abcdef", "abcdefgh", "abcabcabcd"])
    fun `User name should have 6 to 10 characters`(userName: String) {

        // Given
        val slot = slot<String>()
        every { loginRepository.login(capture(slot), any()) } returns (LoginAttemptState.CREDENTIALS_ERROR)

        // When
        val expected = loginUseCase.login(userName, "abcdefg")

        // Then
        assertEquals(expected, LoginAttemptState.CREDENTIALS_ERROR)
        verify(exactly = 1) { loginRepository.login(slot.captured, any()) }
        assertTrue(slot.captured.length in 6..10)

    }

    @ParameterizedTest
    @ValueSource(strings = ["abcde", "abcdabcdefg"])
    fun `User name with less than 6 or more than than 10 chars fail`(userName: String) {

        // Given
        val slot = slot<String>()
        every { loginRepository.login(capture(slot), any()) } returns (LoginAttemptState.CREDENTIALS_ERROR)

        // When
        val expected = loginUseCase.login(userName, "abcabc")

        // Then
        assertNotEquals(expected, LoginAttemptState.SUCCESS)
        verify(exactly = 0) { loginRepository.login(any(), any()) }
        confirmVerified(loginRepository)
    }


    @Test
    fun `Correct credentials result success`() {

        // Given
        every { loginRepository.login("username", "password") } returns LoginAttemptState.SUCCESS

        // When
        val expected = loginUseCase.login("username", "password")

        // Then
        assertEquals(expected, LoginAttemptState.SUCCESS)
        verify(exactly = 1) { loginRepository.login("username", "password") }
        confirmVerified(loginRepository)

    }


}