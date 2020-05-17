package com.smarttoolfactory.tutorial7_1testdrivendevelopment.model.login_auth

enum class AuthenticationState {
    /**
     * Initial state, the user needs to authenticate
     */
    UNAUTHENTICATED,
    /**
     * The user has authenticated successfully
     */
    SUCCESSFUL_AUTHENTICATION,

    /**
     * User left user name or password field empty
     */
    EMPTY_FIELD_ERROR,
    /**
     * Password or username type mismatch. Less than required length and/or missing alpha-numeric regex
     */
    INVALID_FIELD_ERROR,
    /**
     * Authentication failed
     */
    FAILED_AUTHENTICATION,

    /**
     * Max number of invalid login attempts occurred
     */
    MAX_NUMBER_OF_ATTEMPTS_ERROR
}

const val MAX_LOGIN_ATTEMPT = 3
const val PASSWORD_LENGTH_MIN = 6
const val PASSWORD_LENGTH_MAX = 10
const val USER_NAME_LENGTH_MIN = 15