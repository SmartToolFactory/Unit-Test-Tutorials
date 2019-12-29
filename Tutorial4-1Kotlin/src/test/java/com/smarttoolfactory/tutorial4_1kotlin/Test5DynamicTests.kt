package com.smarttoolfactory.tutorial4_1kotlin

import com.smarttoolfactory.tutorial4_1kotlin.model_calculator.Calculator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class Test5DynamicTests {

    private lateinit var calculator: Calculator


    @BeforeEach
    fun setUp() {
        calculator = Calculator()
    }


    @TestFactory
    fun testSquares() = listOf(
        DynamicTest.dynamicTest("when I calculate 1^2 then I get 1") {

            assertEquals(
                1,
                calculator.square(1)
            )
        },
        DynamicTest.dynamicTest("when I calculate 2^2 then I get 4") {
            assertEquals(
                4,
                calculator.square(2)
            )
        },
        DynamicTest.dynamicTest("when I calculate 3^2 then I get 9") {
            assertEquals(
                9,
                calculator.square(3)
            )
        }
    )

    @TestFactory
    fun testSquares2() = listOf(
        1 to 1,
        2 to 4,
        3 to 9,
        4 to 16,
        5 to 25
    )
        .map { (input, expected) ->
            DynamicTest.dynamicTest("when I calculate $input^2 then I get $expected") {
                assertEquals(expected, calculator.square(input))
            }
        }


    private val squaresTestData = listOf(
        1 to 1,
        2 to 4,
        3 to 9,
        4 to 16,
        5 to 25)


    @TestFactory
    fun testSquares3() = squaresTestData
        .map { (input, expected) ->
            DynamicTest.dynamicTest("when I calculate $input^2 then I get $expected") {
                assertEquals(expected, calculator.square(input))
            }
        }


}