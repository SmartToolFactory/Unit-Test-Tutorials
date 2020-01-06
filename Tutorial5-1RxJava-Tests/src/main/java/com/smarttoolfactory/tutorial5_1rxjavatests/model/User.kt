package com.smarttoolfactory.tutorial5_1rxjavatests.model

import java.util.*

data class User(val name: String, val surName: String) {

    val id = UUID.randomUUID().toString()

}