package com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth

interface AccountManager {

    fun saveToken(token: String): Boolean

    fun getToken(): String?

    fun deleteToken()

}