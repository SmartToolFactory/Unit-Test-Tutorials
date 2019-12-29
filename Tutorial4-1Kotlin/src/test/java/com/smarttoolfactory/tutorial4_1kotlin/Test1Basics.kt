package com.smarttoolfactory.tutorial4_1kotlin

import com.smarttoolfactory.tutorial4_1kotlin.model_math_application.CalculatorService
import com.smarttoolfactory.tutorial4_1kotlin.model_math_application.MathApplication
import org.junit.jupiter.api.AfterAll

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.BDDMockito.then
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mockito.`when` as whenever


/**
 * Methods with @BeforeAll and @AfterAll annotations which are static methods should be inside
 * Companion object.
 *
 * Mockito is either used with @ExtendWith(MockitoExtension::class) or
 *  MockitoAnnotations.initMocks(this) is called in method with @BeforeEach annotation
 *
 */
@ExtendWith(MockitoExtension::class)
internal class Test1Basics {

    @InjectMocks
    private lateinit var mathApplication: MathApplication

    @Mock
    private lateinit var calcService: CalculatorService

    @BeforeEach
    fun setUp() {
//        MockitoAnnotations.initMocks(this)
    }


    companion object {

        @BeforeAll
        @JvmStatic
        fun beforeAllTestCases() {
            println("Runs once before all test cases.")
        }

        @AfterAll
        @JvmStatic
        fun afterAllTestCases() {
            println("Runs once after all test cases.")
        }
    }


    @Test
    fun `Add two numbers`() {

        // Given
        whenever(calcService.add(10.0, 20.0)).thenReturn(30.0)

        // When
        val expected = mathApplication.add(10.0, 20.0)

        // Then
        assertEquals(expected, 30.0)
        // Verify that add method is called with 10.0 and 20.0 parameters
        verify(calcService, times(1)).add(10.0, 20.0)
    }

    @Test
    fun `Add two numbers with BDD`() {

        // Given
        BDDMockito.given(calcService.add(10.0, 20.0)).willReturn(30.00)

        // When
        val expected = mathApplication.add(10.0, 20.0)

        // Then
        // Check if adding 10 + 20  with add method is close to 30 with ERROR 0
        assertEquals(expected, 30.0)
        then(calcService).should(times(1)).add(10.0, 20.0)
    }
}