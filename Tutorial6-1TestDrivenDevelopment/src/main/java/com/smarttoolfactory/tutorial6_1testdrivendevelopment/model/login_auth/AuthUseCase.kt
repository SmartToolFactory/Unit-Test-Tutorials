package com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth

class AuthUseCase(
    private val authRepository: AuthRepository,
    private val userManager: AccountManager
) {

    /*
        STEP 1: Throw exception for test to compile and fail
     */
//    fun login(
//        userName: String,
//        password: String,
//        rememberMe: Boolean = false
//    ): AuthenticationState {
//        throw NullPointerException()
//    }


    /*
        STEP3: Check if username or password is empty
     */
        fun login(
        userName: String,
        password: String,
        rememberMe: Boolean = false
    ): AuthenticationState {
       if (userName.isNullOrBlank() || password.isNullOrBlank()) {
           return AuthenticationState.EMPTY_FIELDS
       }else {
           throw NullPointerException()
       }

    }

}