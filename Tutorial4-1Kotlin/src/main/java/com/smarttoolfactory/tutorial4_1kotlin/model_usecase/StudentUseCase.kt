package com.smarttoolfactory.tutorial4_1kotlin.model_usecase

class StudentUseCase(
    private val studentDataSource: StudentDataSource
) {


    fun getStudent(name: String, surname: String): List<Student> {
        return studentDataSource.getStudent(name, surname)
    }


    fun saveStudent(student: Student): Boolean {

        val isPersonExists = studentDataSource.studentExists(student)

        if (isPersonExists) return false


        return if (!isPersonExists) {
            studentDataSource.saveStudent(student)
        } else {
            false
        }


    }

}