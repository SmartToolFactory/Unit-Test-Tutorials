package com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login_auth

interface AuthRepository {

    fun login(userName: String, password: String): User?

}