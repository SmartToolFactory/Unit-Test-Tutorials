package com.smarttoolfactory.tutorial4_1kotlin

import com.smarttoolfactory.tutorial4_1kotlin.model_calculator.Calculator
import com.smarttoolfactory.tutorial4_1kotlin.model_person.Person
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import java.time.LocalDate

class Test5DynamicTests {

    private lateinit var calculator: Calculator


    @BeforeEach
    fun setUp() {
        calculator = Calculator()
    }


    @TestFactory
    fun testSquares() = listOf(

        dynamicTest("when I calculate 1^2 then I get 1") {

            assertEquals(
                1,
                calculator.square(1)
            )
        },

        dynamicTest("when I calculate 2^2 then I get 4") {
            assertEquals(
                4,
                calculator.square(2)
            )
        },

        dynamicTest("when I calculate 3^2 then I get 9") {
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
            dynamicTest("when I calculate $input^2 then I get $expected") {
                assertEquals(expected, calculator.square(input))
            }
        }


    private val squaresTestData = listOf(
        1 to 1,
        2 to 4,
        3 to 9,
        4 to 16,
        5 to 25
    )


    @TestFactory
    fun testSquares3() = squaresTestData
        .map { (input, expected) ->
            dynamicTest("when I calculate $input^2 then I get $expected") {
                assertEquals(expected, calculator.square(input))
            }
        }


    @TestFactory
    fun `Run multiple tests`(): Collection<DynamicTest> {
        val persons = listOf(
            Person("John", "Doe", LocalDate.of(1969, 5, 20)),
            Person("Jane", "Smith", LocalDate.of(1997, 11, 21)),
            Person("Ivan", "Ivanov", LocalDate.of(1994, 2, 12))
        )
        val minAgeFilter = 18
        return persons.map {
            dynamicTest("Check person $it on age greater or equals $minAgeFilter") {
                assertTrue(it.age >= minAgeFilter)
            }
        }.toList()
    }


}