package com.smarttoolfactory.tutorial4_1kotlin.model_person

import android.annotation.SuppressLint
import java.time.LocalDate
import java.time.Period

@SuppressLint("NewApi")
data class Person(val firstName: String, val lastName: String, val birthDate: LocalDate? = null) {
    val age
        get() = Period.between(this.birthDate, LocalDate.now()).years
}