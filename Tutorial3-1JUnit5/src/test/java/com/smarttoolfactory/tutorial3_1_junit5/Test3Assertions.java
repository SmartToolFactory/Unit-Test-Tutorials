package com.smarttoolfactory.tutorial3_1_junit5;


import com.smarttoolfactory.tutorial3_1_junit5.model_calculator.Calculator;
import com.smarttoolfactory.tutorial3_1_junit5.model_person.Person;

import org.junit.jupiter.api.Test;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 4	            JUnit 5
 * ----------           ----------
 * fail	                fail
 * assertTrue	        assertTrue
 * assertThat	        N/A
 * assertSame	        assertSame
 * assertNull	        assertNull
 * assertNotSame	    assertNotSame
 * assertNotEquals	    assertNotEquals
 * assertNotNull	    assertNotNull
 * assertFalse	        assertFalse
 * assertEquals	        assertEquals
 * assertArrayEquals	assertArrayEquals
 * <p>
 * N/A                  assertIterableEquals
 * N/A	                assertAll
 * N/A	                assertThrows
 * N/A                  assertTimeOut
 * N/A                  assertTimeoutPreemptively
 */
class Test3Assertions {

    private final Calculator calculator = new Calculator();

    private final Person person = new Person("Jane", "Doe");

    @Test
    void standardAssertions() {

        assertEquals(2, calculator.add(1, 1));
        assertEquals(4, calculator.multiply(2, 2),
                "The optional failure message is now the last parameter");

        assertTrue('a' < 'b', () -> "Assertion messages can be lazily evaluated -- "
                + "to avoid constructing complex messages unnecessarily.");
    }


    /**
     * Assertion accepts a heading, that will be included in the message string
     * for the MultipleFailureError, and a Stream of Executable.
     */
    @Test
    void groupAssertions() {
        int[] numbers = {0, 1, 2, 3, 4};
        assertAll("numbers",
                () -> assertEquals(numbers[0], 0),
                () -> assertEquals(numbers[3], 3),
                () -> assertEquals(numbers[4], 4)
        );
    }


    @Test
    void groupedAssertions() {
        // In a grouped assertion all assertions are executed, and all
        // failures will be reported together.
        assertAll("Person Properties",
                () -> assertEquals("Jane", person.getFirstName()),
                () -> assertEquals("Doe", person.getLastName())
        );
    }


    /**
     * Each group depends it's block only. Even if first group might fail it does not
     * mean that second group of test also should fail
     */
    @Test
    void dependentAssertions() {
        // Within a code block, if an assertion fails the
        // subsequent code in the same block will be skipped.
        assertAll("properties",

                // 🔥 First Assertion Group
                () -> {

                    System.out.println("First group test STARTED");

                    String firstName = person.getFirstName();

                    assertNotNull(firstName);

                    // Executed only if the previous assertion is valid.
                    assertAll("first name",
                            () -> assertTrue(firstName.startsWith("J")),
                            () -> assertTrue(firstName.endsWith("e"))
                    );

                    System.out.println("First group test PASSED");
                },

                // 🔥 Second Assertion Group
                () -> {

                    System.out.println("Second group test STARTED");

                    // Grouped assertion, so processed independently
                    // of results of first name assertions.
                    String lastName = person.getLastName();
                    assertNotNull(lastName);

                    // Executed only if the previous assertion is valid.
                    assertAll("last name",
                            () -> assertTrue(lastName.startsWith("D")),
                            () -> assertTrue(lastName.endsWith("e"))
                    );

                    System.out.println("Second group test PASSED");

                }

        );
    }

    @Test
    void exceptionTesting() {
        Exception exception = assertThrows(ArithmeticException.class, () ->
                calculator.divide(1, 0));
        assertEquals("/ by zero", exception.getMessage());
    }

    @Test
    void timeoutNotExceeded() {

        // The following assertion succeeds.
        assertTimeout(ofMinutes(2), () -> {
            // Perform task that takes less than 2.
            System.out.println("Testing");
        });
    }

    @Test
    void timeoutNotExceededWithResult() {
        // The following assertion succeeds, and returns the supplied object.
        String actualResult = assertTimeout(ofMinutes(2), () -> {
            return "a result";
        });
        assertEquals("a result", actualResult);
    }

    @Test
    void timeoutNotExceededWithMethod() {
        // The following assertion invokes a method reference and returns an object.
        String actualGreeting = assertTimeout(ofMinutes(2), Test3Assertions::greeting);
        assertEquals("Hello, World!", actualGreeting);
    }

    @Test
    void timeoutExceeded() {
        // The following assertion FAILS with an error message similar to:
        // execution exceeded timeout of 10 ms by 91 ms
        assertTimeout(ofMillis(10), () -> {
            System.out.println("Thread: " + Thread.currentThread().getName());

            // Simulate task that takes more than 10 ms.
            Thread.sleep(100);
        });
    }

    /**
     * This test runs on a thread other than main thread.
     */
    @Test
    void timeoutExceededWithPreemptiveTermination() {
        // The following assertion FAILS with an error message similar to:
        // execution timed out after 10 ms
        assertTimeoutPreemptively(ofMillis(10), () -> {
            // prints -> Thread: pool-1-thread-1
            System.out.println("Thread: " + Thread.currentThread().getName());
            // Simulate task that takes more than 10 ms.
            Thread.sleep(100);
        });
    }

    private static String greeting() {
        return "Hello, World!";
    }

}
