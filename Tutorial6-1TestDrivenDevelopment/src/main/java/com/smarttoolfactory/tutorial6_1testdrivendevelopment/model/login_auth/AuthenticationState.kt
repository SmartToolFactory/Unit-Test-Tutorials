package com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth

enum class AuthenticationState {
    /**
     * Initial state, the user needs to authenticate
     */
    UNAUTHENTICATED,
    /**
     * The user has authenticated successfully
     */
    AUTHENTICATED,

    /**
     * User left user name or password field empty
     */
    EMPTY_FIELDS,
    /**
     * Password or username type mismatch. Less than required length and/or missing alpha-numeric regex
     */
    INVALID_FIELDS,
    /**
     * Authentication failed
     */
    FAILED_AUTHENTICATION,

    /**
     * Max number of invalid login attempts occurred
     */
    MAX_NUMBER_OF_INVALID_ATTEMPTS
}