package com.smarttoolfactory.tutorial4_1kotlin

import com.smarttoolfactory.tutorial4_1kotlin.model_calculator.Calculator
import com.smarttoolfactory.tutorial4_1kotlin.model_person.Person
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import java.time.Duration

class Test2Assertions {

    private val calculator: Calculator = Calculator()

    private val person: Person = Person("Jane", "Doe")

    @Test
    fun `Standard Assertions`() {

        assertEquals(2, calculator.add(1, 1))

        assertEquals(
            4, calculator.multiply(2, 2),
            "The optional failure message is now the last parameter"
        )
        assertTrue('a' < 'b') {
            ("Assertion messages can be lazily evaluated -- "
                    + "to avoid constructing complex messages unnecessarily.")
        }
    }

    @Test
    fun `Multiple Assertions`() {

        assertAll(
            Executable { assertEquals(1, calculator.square(1)) },
            Executable { assertEquals(4, calculator.square(2)) },
            Executable { assertEquals(9, calculator.square(3)) }
        )
    }


    /**
     * Assertion accepts a heading, that will be included in the message string
     * for the MultipleFailureError, and a Stream of Executable.
     */
    @Test
    fun `Multiple Assertions with Heading`() {

        val numbers = intArrayOf(0, 1, 2, 3, 4)

        assertAll(
            "numbers",
            Executable { assertEquals(numbers[0], 0) },
            Executable { assertEquals(numbers[3], 3) },
            Executable { assertEquals(numbers[4], 4) }
        )
    }


    @Test
    fun `Multiple Assertions with Heading 2`() {

        // In a grouped assertion all assertions are executed, and all
        // failures will be reported together.
        assertAll(
            "Person Properties",
            Executable { assertEquals("Jane", person.firstName) },
            Executable { assertEquals("Doe", person.lastName) }
        )
    }


    /**
     * Each group depends it's block only. Even if first group might fail it does not
     * mean that second group of test also should fail
     */
    @Test
    fun `Dependent Assertions`() {

        // Within a code block, if an assertion fails the
        // subsequent code in the same block will be skipped.
        assertAll(
            "properties",

            // 🔥 First Assertion Group
            Executable {
                println("First group test STARTED")
                val firstName: String = person.firstName

                assertNotNull(firstName)

                // Executed only if the previous assertion is valid.
                assertAll(
                    "first name",
                    Executable { assertTrue(firstName.startsWith("J")) },
                    Executable { assertTrue(firstName.endsWith("e")) }
                )
                println("First group test PASSED")
            },

            // 🔥 Second Assertion Group
            Executable {

                println("Second group test STARTED")
                // Grouped assertion, so processed independently
                // of results of first name assertions.
                val lastName: String = person.lastName

                assertNotNull(lastName)
                // Executed only if the previous assertion is valid.
                assertAll(
                    "last name",
                    Executable { assertTrue(lastName.startsWith("D")) },
                    Executable { assertTrue(lastName.endsWith("e")) }
                )

                println("Second group test PASSED")
            }
        )
    }


    @Test
    fun `Timeout Not Exceeded`() { // The following assertion succeeds.
        assertTimeout(Duration.ofMinutes(2)) {
            // Perform task that takes less than 2.
            println("Testing")
        }
    }

    @Test
    fun `Timeout Not Exceeded With Result`() {

        // The following assertion succeeds, and returns the supplied object.
        val actualResult = assertTimeout<String>(Duration.ofMinutes(2)) {
            "a result"
        }
        assertEquals("a result", actualResult)
    }


    @Test
    fun `Timeout Exceeds`() {

        // The following assertion FAILS with an error message similar to:
        // execution exceeded timeout of 10 ms by 91 ms
        assertTimeout(Duration.ofMillis(10)) {
            println("Thread: " + Thread.currentThread().name)
            // Simulate task that takes more than 10 ms.
            Thread.sleep(100)
        }
    }

    /**
     * This test runs on a thread other than main thread.
     */
    @Test
    fun `Timeout Exceeded With Preemptive Termination`() {

        // The following assertion FAILS with an error message similar to:
        // execution timed out after 10 ms

        assertTimeoutPreemptively(Duration.ofMillis(10)) {

            // prints -> Thread: pool-1-thread-1
            println("Thread: " + Thread.currentThread().name)
            // Simulate task that takes more than 10 ms.
            Thread.sleep(100)

        }
    }


}