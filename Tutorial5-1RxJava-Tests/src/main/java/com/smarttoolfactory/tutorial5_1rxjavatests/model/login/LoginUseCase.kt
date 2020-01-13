package com.smarttoolfactory.tutorial5_1rxjavatests.model.login


class LoginUseCase(
    private val loginRepository: LoginRepository
) {

    fun login(userName: String, password: String): LoginAttemptState {

        return if (userName.isEmpty() || password.isEmpty()) {
            LoginAttemptState.EMPTY_FIELD_ERROR
        } else if (userName.length < 6 || userName.length > 10) {
            LoginAttemptState.CREDENTIALS_ERROR
        } else {
            loginRepository.login(userName, password)
        }

    }
}

enum class LoginAttemptState {
    SUCCESS,
    EMPTY_FIELD_ERROR,
    CREDENTIALS_ERROR
}