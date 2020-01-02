package com.smarttoolfactory.tutorial4_1kotlin


import com.smarttoolfactory.tutorial4_1kotlin.model_usecase.Student
import com.smarttoolfactory.tutorial4_1kotlin.model_usecase.StudentDataSource
import com.smarttoolfactory.tutorial4_1kotlin.model_usecase.StudentUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mockito.`when` as whenever


/*
    🔥🔥 verifyNoMoreInteractions methods are not working with JUnit5 but works with JUnit4
    as of Mockito version 3.0
 */
@ExtendWith(MockitoExtension::class)
class Test9Mock1Interface {


    @InjectMocks
    private lateinit var studentUseCase: StudentUseCase

    @Mock
    private lateinit var studentDataSource: StudentDataSource


    @Test
    fun `Add a new Student`() {

        // Given
        val student = Student("Walter", "White", 50)

        whenever(studentDataSource.studentExists(student)).thenReturn(false)
        whenever(studentDataSource.saveStudent(student)).thenReturn(true)

        // When
        val expected = studentUseCase.saveStudent(student)

        // Then
        assertEquals(expected, true)

        verify(studentDataSource, times(1)).studentExists(student)
        verify(studentDataSource, times(1)).saveStudent(student)


    }


    @Test
    fun `Add an existing Student`() {

        // Given
        val student = Student("Walter", "White", 50)

        whenever(studentDataSource.studentExists(student)).thenReturn(true)

        // When
        val expected = studentUseCase.saveStudent(student)

        // Then
        assertEquals(expected, false)

        verify(studentDataSource, times(1)).studentExists(student)
        verify(studentDataSource, times(0)).saveStudent(student)

    }


    @Test
    fun `Getting existing student`() {

        // Given
        val student = Student("Walter", "White", 50)
        val actual = listOf(student)

        whenever(studentDataSource.getStudent(student.name, student.surName)).thenReturn(actual)

        // When
        val expected = studentUseCase.getStudent(student.name, student.surName)

        // Then

        assertIterableEquals(expected, actual)
        assertTrue(expected.contains(student))
        verify(studentDataSource, times(1)).getStudent(student.name, student.surName)

    }

    @Test
    fun `Getting non-existent student should return empty list`() {

        // Given
        whenever(studentUseCase.getStudent(anyString(), anyString())).thenReturn(listOf())

        // When
        val expected = studentUseCase.getStudent(anyString(), anyString())

        // Then
        assertTrue(expected.isEmpty())

        verify(studentDataSource, times(1)).getStudent(anyString(), anyString())

    }


    /*
     * with Behavior Driven Development
     */

    @Test
    fun `Add new Student with BDD`() {

        // Given
        val student = Student("Walter", "White", 50)

        given(studentDataSource.studentExists(student)).willReturn(false)
        given(studentDataSource.saveStudent(student)).willReturn(true)

        // When
        val expected = studentUseCase.saveStudent(student)

        // Then
        assertEquals(expected, true)

        then(studentDataSource).should(times(1)).studentExists(student)
        then(studentDataSource).should(times(1)).saveStudent(student)


    }

    @Test
    fun `Add an existing Student with BDD`() {

        // Given
        val student = Student("Walter", "White", 50)

        given(studentDataSource.studentExists(student)).willReturn(true)

        // When
        val expected = studentUseCase.saveStudent(student)

        // Then
        assertEquals(expected, false)

        then(studentDataSource).should(times(1)).studentExists(student)
        then(studentDataSource).should(times(0)).saveStudent(student)

    }


    @Test
    fun `Get an existing student with BDD`() {

        // Given
        val student = Student("Walter", "White", 50)
        val actual = listOf(student)

        given(studentDataSource.getStudent(student.name, student.surName)).willReturn(actual)

        // When
        val expected = studentUseCase.getStudent(student.name, student.surName)

        // Then
        assertIterableEquals(expected, actual)
        assertTrue(expected.contains(student))
        then(studentDataSource).should(times(1)).getStudent(student.name, student.surName)

    }

    @Test
    fun `Getting non-existent student should return empty list with BDD`() {

        // Given
        given(studentUseCase.getStudent(anyString(), anyString())).willReturn(listOf())

        // When
        val expected = studentUseCase.getStudent(anyString(), anyString())

        // Then
        assertTrue(expected.isEmpty())

        then(studentDataSource).should(times(1)).getStudent(anyString(), anyString())

    }

}