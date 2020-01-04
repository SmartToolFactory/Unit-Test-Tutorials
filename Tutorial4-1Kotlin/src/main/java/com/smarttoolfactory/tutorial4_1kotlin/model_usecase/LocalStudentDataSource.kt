package com.smarttoolfactory.tutorial4_1kotlin.model_usecase

class LocalStudentDataSource : StudentDataSource {

    private val list = arrayListOf<Student>()

    override fun getStudent(name: String, surname: String): List<Student> {
        return list.filter {
            it.name == name && it.surName == surname
        }
    }

    override fun saveStudent(student: Student): Boolean {
        return if (list.contains(student)) {
            false
        } else {
            list.add(student)
        }
    }

    override fun studentExists(student: Student): Boolean {
        return list.contains(student)
    }


}