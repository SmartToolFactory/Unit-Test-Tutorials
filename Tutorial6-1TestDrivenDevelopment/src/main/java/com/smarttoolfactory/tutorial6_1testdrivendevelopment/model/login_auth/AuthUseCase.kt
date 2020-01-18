package com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth

import com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth.AuthenticationState.*

class AuthUseCase(
    private val authRepository: AuthRepository,
    private val accountManager: AccountManager
) {

    private var loginAttempt = 0

    /*
        🔥 STEP 2: Throw exception for test to compile and fail

     */
//    fun login(
//        userName: String,
//        password: String,
//        rememberMe: Boolean = false
//    ): AuthenticationState {
//    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }


    /*
        🔥 STEP3: Add code to return EMPTY_FIELD_ERROR if username or password is empty
     */
//    fun login(
//        userName: String,
//        password: String,
//        rememberMe: Boolean = false
//    ): AuthenticationState {
//
//
//        if (userName.isNullOrBlank() || password.isNullOrBlank()) {
//            return EMPTY_FIELD_ERROR
//        } else {
//    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//    }


    /*
       🔥 STEP3: Add code to return EMPTY_FIELD_ERROR if password length does not match
    */
//    fun login(
//        userName: String,
//        password: String,
//        rememberMe: Boolean = false
//    ): AuthenticationState {
//
//        return if (userName.isNullOrBlank() || password.isNullOrBlank()) {
//            return EMPTY_FIELD_ERROR
//        } else if (!checkIfPasswordIsValid(password)) {
//            INVALID_FIELD_ERROR
//        } else {
//            throw TestFailedException()
//        }
//
//    }

    /*
        🔥 STEP5: Add code to return EMPTY_FIELD_ERROR if username is not valid expression
    */
//    fun login(
//        userName: String,
//        password: String,
//        rememberMe: Boolean = false
//    ): AuthenticationState {
//
//        return if (userName.isNullOrBlank() || password.isNullOrBlank()) {
//            return EMPTY_FIELD_ERROR
//        } else if (!checkUserNameIsValid(userName) || !checkIfPasswordIsValid(password)) {
//            INVALID_FIELD_ERROR
//        } else {
//            throw TestFailedException()
//        }
//
//    }

    /*
        🔥 STEP7-9: Add code to return invalid authentication when user name or password is wrong
     */
//    fun login(
//        userName: String,
//        password: String,
//        rememberMe: Boolean = false
//    ): AuthenticationState {
//
//        return if (userName.isNullOrBlank() || password.isNullOrBlank()) {
//            return EMPTY_FIELD_ERROR
//        } else if (!checkUserNameIsValid(userName) || !checkIfPasswordIsValid(password)) {
//            INVALID_FIELD_ERROR
//        } else {
//            // Concurrent Authentication via mock that returns AUTHENTICATED, or FAILED_AUTHENTICATION
//
//            val authResponse =
//                authRepository.login(userName, password, rememberMe)
//
//            val authenticationPass = authResponse?.authenticated ?: false
//
//            if (authenticationPass) {
//                SUCCESSFUL_AUTHENTICATION
//
//            } else {
//                FAILED_AUTHENTICATION
//            }
//        }
//
//    }


    /*
        🔥 STEP 11 Add code to check maximum number of attempts
     */
    fun login(
        userName: String,
        password: String,
        rememberMe: Boolean = false
    ): AuthenticationState {

        return if (userName.isNullOrBlank() || password.isNullOrBlank()) {
            return EMPTY_FIELD_ERROR
        } else if (!checkUserNameIsValid(userName) || !checkIfPasswordIsValid(password)) {
            INVALID_FIELD_ERROR
        } else {
            // Concurrent Authentication via mock that returns AUTHENTICATED, or FAILED_AUTHENTICATION

            val authenticationPass = getAccountResponse(userName, password, rememberMe)


            if (authenticationPass) {

                loginAttempt = 0
                SUCCESSFUL_AUTHENTICATION

            } else {

                loginAttempt++

                if (loginAttempt >= MAX_LOGIN_ATTEMPT) {
                    MAX_NUMBER_OF_ATTEMPTS_ERROR
                } else {
                    FAILED_AUTHENTICATION
                }
            }
        }

    }

    private fun getAccountResponse(
        userName: String,
        password: String,
        rememberMe: Boolean
    ): Boolean {

        val authResponse =
            authRepository.login(userName, password)

        val authenticationPass = authResponse?.authenticated ?: false


        saveToken(authResponse, rememberMe)

        return authenticationPass
    }

    /*
       🔥 STEP 13 Add code to save token when remember me is selected
    */
    private fun saveToken(
        authResponse: AuthResponse?,
        rememberMe: Boolean
    ) {
        authResponse?.token?.let {
            if (rememberMe) accountManager.saveToken(it)
        }
    }


    private fun checkUserNameIsValid(field: String): Boolean {
        return field.length > 15 && field.endsWith("@example.com")

    }

    private fun checkIfPasswordIsValid(field: String): Boolean {
        return field.length in 6..10
    }


    /*
      🔥 STEP 15 Add code to get token when it's saved
   */
    fun getToken(): String? {
        return accountManager.getToken()

    }

    fun logOut() {
        accountManager.deleteToken()
    }

}

