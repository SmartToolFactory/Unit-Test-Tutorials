package com.smarttoolfactory.tutorial4_1kotlin.model_usecase


interface StudentDataSource {

    fun getStudent(name: String, surname: String): List<Student>

    fun saveStudent(student: Student): Boolean

    fun studentExists(student: Student): Boolean


}