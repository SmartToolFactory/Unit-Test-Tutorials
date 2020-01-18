package com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth

class AuthUseCase(
    private val loginRepository2: AuthRepository,
    private val userManager: AccountManager
) {
    fun login(
        userName: String,
        password: String,
        rememberMe: Boolean = false
    ): AuthenticationState {
        throw NullPointerException()
    }

}