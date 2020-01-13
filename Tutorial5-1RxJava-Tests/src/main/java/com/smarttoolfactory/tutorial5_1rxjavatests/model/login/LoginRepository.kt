package com.smarttoolfactory.tutorial5_1rxjavatests.model.login

interface LoginRepository {

    fun login(userName: String, password: String): LoginAttemptState
}