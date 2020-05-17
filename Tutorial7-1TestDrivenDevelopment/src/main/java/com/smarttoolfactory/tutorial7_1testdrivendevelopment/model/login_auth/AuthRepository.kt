package com.smarttoolfactory.tutorial7_1testdrivendevelopment.model.login_auth

interface AuthRepository {

    fun login(userName: String, password: String): AuthResponse?

}