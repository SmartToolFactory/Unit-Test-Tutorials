package com.smarttoolfactory.tutorial4_1kotlin

import com.smarttoolfactory.tutorial4_1kotlin.model_calculator.Calculator
import com.smarttoolfactory.tutorial4_1kotlin.model_math_application.CalculatorService
import com.smarttoolfactory.tutorial4_1kotlin.model_math_application.MathApplication
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.mockito.BDDMockito.then
import org.mockito.BDDMockito.willThrow
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.lang.RuntimeException
import org.mockito.Mockito.`when` as whenever

@ExtendWith(MockitoExtension::class)
internal class Test3ExceptionHandling {

    @InjectMocks
    private lateinit var mathApplication: MathApplication

    @Mock
    private lateinit var calcService: CalculatorService

    @Test
    fun `Divide throws ArithmeticException`() {

        val exception: Exception = assertThrows(
            ArithmeticException::class.java
        ) { divide(1, 0) }

        assertEquals("/ by zero", exception.message)

        assertTrue(exception.message!!.contains("zero"))
    }

    private fun divide(input: Int, divide: Int): Int {
        return input / divide
    }

    @Test
    fun `Add throws ArithmeticException`() {

        // Given
        whenever(
            calcService.add(
                10.0,
                20.0
            )
        ).thenThrow(RuntimeException("Add operation not implemented"))

        // When
        val exception = assertThrows(RuntimeException::class.java) {
            mathApplication.add(10.0, 20.0)
        }


        // Then
        verify(calcService, times(1)).add(10.0, 20.0)
        assertEquals("Add operation not implemented", exception.message)
        assertTrue(exception.message!!.contains("Add operation not implemented"))
    }

    @Test
    fun `Add throws ArithmeticException with BDD`() {

        // Given
        willThrow(RuntimeException("Add operation not implemented"))
            .given(calcService).add(10.0, 20.0)

        // When
        val exception = assertThrows(RuntimeException::class.java) {
            mathApplication.add(10.0, 20.0)
        }

        // Then
        then(calcService).should(times(1)).add(10.0, 20.0)
        assertEquals("Add operation not implemented", exception.message)
        assertTrue(exception.message!!.contains("Add operation not implemented"))
    }

    @Test
    fun `exception absence testing`() {
        val calculator = Calculator()

       assertDoesNotThrow(
            { calculator.divide(0, 1) },
            "Should not throw an exception"
        )

    }


}