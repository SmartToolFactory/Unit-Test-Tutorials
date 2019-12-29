package com.smarttoolfactory.tutorial4_1kotlin

import com.smarttoolfactory.tutorial4_1kotlin.model_math_application.CalculatorService
import com.smarttoolfactory.tutorial4_1kotlin.model_math_application.MathApplication

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mockito.`when` as whenever

@ExtendWith(MockitoExtension::class)
class Test4OrderedVerification {

    private lateinit var mathApplication: MathApplication
    private lateinit var calcService: CalculatorService

    @BeforeEach
    fun setUp() {

        mathApplication = MathApplication()
        calcService = mock(CalculatorService::class.java)

        mathApplication.calcService = calcService
    }

    @Test
    fun `Add and Subtract with Order`() {

        // Given
        whenever(calcService.add(20.0, 10.0)).thenReturn(30.0)
        whenever(calcService.subtract(20.0, 10.0)).thenReturn(10.0)

        //create an inOrder verifier for a single mock
        val inOrder = inOrder(calcService)

        // When
        val expectedAdd = mathApplication.add(20.0, 10.0)
        val expectedSubtract = mathApplication.subtract(20.0, 10.0)

        // Then
        inOrder.verify(calcService).add(20.0, 10.0)
        inOrder.verify(calcService).subtract(20.0, 10.0)

        assertEquals(expectedAdd, 30.0)
        assertEquals(expectedSubtract, 10.0)

    }

    @Test
    fun `Add and Subtract with Order with BDD`() {

        // Given
        given(calcService.add(20.0, 10.0)).willReturn(30.0)
        given(calcService.subtract(20.0, 10.0)).willReturn(10.0)

        //create an inOrder verifier for a single mock
        val inOrder = inOrder(calcService)

        // When
        val expectedAdd = mathApplication.add(20.0, 10.0)
        val expectedSubtract = mathApplication.subtract(20.0, 10.0)

        // Then
        then(calcService).should(inOrder).add(20.0, 10.0)
        then(calcService).should(inOrder).subtract(20.0, 10.0)

        assertEquals(expectedAdd, 30.0)
        assertEquals(expectedSubtract, 10.0)

    }

    @Test
    fun `Test Order of Void Methods`() {

        // Given
        val firstMock = mock(ServiceClassA::class.java)
        val secondMock = mock(ServiceClassB::class.java)

        //create inOrder object passing any mocks that need to be verified in order
        val inOrder = inOrder(firstMock, secondMock)

        doNothing().`when`(firstMock).methodOne()
        doNothing().`when`(secondMock).methodTwo()

        // When
        firstMock.methodOne()
        secondMock.methodTwo()

        // Then
        inOrder.verify(firstMock).methodOne()
        inOrder.verify(secondMock).methodTwo()


    }

    @Test
    fun `Test Order of Void Methods with BDD`() {

        // Given
        val firstMock = mock(ServiceClassA::class.java)
        val secondMock = mock(ServiceClassB::class.java)

        //create inOrder object passing any mocks that need to be verified in order
        val inOrder = inOrder(firstMock, secondMock)

        willDoNothing().given(firstMock).methodOne()
        willDoNothing().given(secondMock).methodTwo()

        // When
        // 🔥 void methods
        firstMock.methodOne()
        secondMock.methodTwo()

        // Then
        then(firstMock).should(inOrder).methodOne()
        then(secondMock).should(inOrder).methodTwo()

    }


    open class ServiceClassA {

        open fun methodOne() {}

    }

    open class ServiceClassB {
        open fun methodTwo() {}
    }
}