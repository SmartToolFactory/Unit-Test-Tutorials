package com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login


class LoginUseCase(
    private val loginRepository: LoginRepo
) {

    fun login(userName: String, password: String): LoginState {

        return if (userName.isEmpty() || password.isEmpty()) {
            LoginState.EMPTY_FIELD_ERROR
        } else if (password.length < 6 || password.length > 10) {
            LoginState.CREDENTIALS_ERROR
        } else {
            loginRepository.login(userName, password)
        }

    }
}

enum class LoginState {
    SUCCESS,
    EMPTY_FIELD_ERROR,
    CREDENTIALS_ERROR
}