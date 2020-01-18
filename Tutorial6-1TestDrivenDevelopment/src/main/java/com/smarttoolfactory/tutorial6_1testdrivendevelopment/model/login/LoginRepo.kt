package com.smarttoolfactory.tutorial6_1testdrivendevelopment.model.login

interface LoginRepo {

    fun login(userName: String, password: String): LoginState
}