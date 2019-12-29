package com.smarttoolfactory.tutorial4_1kotlin.model_login_manager

class LoginManagerImpl {
    var loginManager: ILoginManager? =
        null

    fun login(
        email: String?,
        password: String?
    ): User? {
        return loginManager!!.login(email, password)
    }

    fun validate(email: String?, password: String?): String? {
        return loginManager!!.validate(email, password)
    }
}