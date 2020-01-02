package com.smarttoolfactory.tutorial4_1kotlin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class Test7NestedTests {

    @Nested
    inner class `String tests` {

        @ParameterizedTest
        @CsvSource("test,TEST", "tEst,TEST", "Java,JAVA")
        fun `Uppercase of the string is valid`(input: String, expected: String) {
            println("Test input: $input, expected: $expected")
            val actualValue = input.toUpperCase()
            assertEquals(expected, actualValue)
        }


    }

    @Nested
    inner class `Integer tests` {

        @ParameterizedTest
        @ValueSource(ints = [2, 4, 6, 8])
        fun `Odd number check`(number: Int) {

            assertTrue(number % 2 == 0)
        }
    }

}