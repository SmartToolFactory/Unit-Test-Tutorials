package com.smarttoolfactory.tutorial4_1kotlin


import com.smarttoolfactory.tutorial4_1kotlin.model_usecase.LocalStudentDataSource
import com.smarttoolfactory.tutorial4_1kotlin.model_usecase.Student
import com.smarttoolfactory.tutorial4_1kotlin.model_usecase.StudentUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mockito.`when` as whenever


/**
 * Create file inside resources/mockito-extensions with name org.mockito.plugins.MockitoMaker
 * and add mock-maker-inline line to it
 */
@ExtendWith(MockitoExtension::class)
class Test9Mock2FinalClass {


    @InjectMocks
    private lateinit var studentUseCase: StudentUseCase

    // This is a concrete class that implements StudentDataSource
    @Mock
    private lateinit var localStudentDataSource: LocalStudentDataSource


    @Test
    fun `Add a new Student`() {

        // Given
        val student = Student("Walter", "White", 50)

        whenever(localStudentDataSource.studentExists(student)).thenReturn(false)
        whenever(localStudentDataSource.saveStudent(student)).thenReturn(true)

        // When
        val expected = studentUseCase.saveStudent(student)

        // Then
        assertEquals(expected, true)

        verify(localStudentDataSource, times(1)).studentExists(student)
        verify(localStudentDataSource, times(1)).saveStudent(student)

    }


    @Test
    fun `Add an existing Student`() {

        // Given
        val student = Student("Walter", "White", 50)

        whenever(localStudentDataSource.studentExists(student)).thenReturn(true)

        // When
        val expected = studentUseCase.saveStudent(student)

        // Then
        assertEquals(expected, false)

        verify(localStudentDataSource, times(1)).studentExists(student)
        verify(localStudentDataSource, times(0)).saveStudent(student)

    }


    @Test
    fun `Getting existing student`() {

        // Given
        val student = Student("Walter", "White", 50)
        val actual = listOf(student)

        whenever(
            localStudentDataSource.getStudent(
                student.name,
                student.surName
            )
        ).thenReturn(actual)

        // When
        val expected = studentUseCase.getStudent(student.name, student.surName)

        // Then
        assertIterableEquals(expected, actual)
        assertTrue(expected.contains(student))
        verify(localStudentDataSource, times(1)).getStudent(student.name, student.surName)

    }

    @Test
    fun `Getting non-existent student should return empty list`() {

        // Given
        whenever(studentUseCase.getStudent(anyString(), anyString())).thenReturn(listOf())

        // When
        val expected = studentUseCase.getStudent(anyString(), anyString())

        // Then
        assertTrue(expected.isEmpty())

        verify(localStudentDataSource, times(1)).getStudent(anyString(), anyString())

    }


}