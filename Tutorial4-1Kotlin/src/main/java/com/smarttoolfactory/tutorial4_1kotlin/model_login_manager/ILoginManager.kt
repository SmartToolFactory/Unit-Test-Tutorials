package com.smarttoolfactory.tutorial4_1kotlin.model_login_manager

interface ILoginManager {


    fun login(
        email: String?,
        password: String?
    ): User?

    fun validate(email: String?, password: String?): String?
}