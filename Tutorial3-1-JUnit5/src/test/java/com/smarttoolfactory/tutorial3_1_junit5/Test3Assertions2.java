package com.smarttoolfactory.tutorial3_1_junit5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;


class Test3Assertions2 {

    @Test
    @DisplayName("Should contain the same elements")
    void shouldContainSameElements() {

        final List<Integer> FIRST = asList(1, 2, 3);
        final List<Integer> SECOND = asList(1, 2, 3);

        assertIterableEquals(FIRST, SECOND);
    }

    @Test
    @DisplayName("Should throw the correct exception")
    void shouldThrowCorrectException() {
        assertThrows(
                NullPointerException.class,
                () -> {
                    throw new NullPointerException();
                }
        );
    }

    @Test
    @DisplayName("Should not throw an exception")
    void shouldNotThrowException() {
        assertDoesNotThrow(() -> {
        });
    }

    @Test
    @DisplayName("Should not throw an exception")
    void shouldNotThrowExceptionAndReturnString() {
        String message = assertDoesNotThrow(() -> {
            return "Hello World!";
        });
        assertEquals("Hello World!", message);
    }


    /**
     * The assertArrayEquals assertion verifies that the expected and the actual arrays are equals:
     * <p></p>
     * If the arrays aren't equal, the message “Arrays should be equal” will be displayed as output.
     */
    @Test
    void whenAssertingArraysEquality_thenEqual() {
        char[] expected = {'J', 'u', 'p', 'i', 't', 'e', 'r'};
        char[] actual = "Jupiter".toCharArray();

        assertArrayEquals(expected, actual, "Arrays should be equal");
    }


    /**
     * When we want to assert that the expected and the actual refer to the same Object,
     * we must use the assertSame assertion:
     */
    @Test
    void whenAssertingSameObject_thenSuccessfull() {
        String language = "Java";
        Optional<String> optional = Optional.of(language);

        assertSame(language, optional.get());
    }

    /**
     * One of the new assertion introduced in JUnit 5 is assertAll.
     * <p>
     * This assertion allows the creation of grouped assertions, where all the assertions are executed
     * and their failures are reported together.
     * In details, this assertion accepts a heading, that will be included in the message string
     * for the MultipleFailureError, and a Stream of Executable.
     */
    @Test
    void givenMultipleAssertion_whenAssertingAll_thenOK() {
        assertAll(
                "heading",
                () -> assertEquals(4, 2 * 2, "4 is 2 times 2"),
                () -> assertEquals("java", "JAVA".toLowerCase()),
                () -> assertNull(null, "null is equal to null")
        );
    }

    /**
     * The assertIterableEquals asserts that the expected and the actual iterables are deeply equal.
     * <p></p>
     * In order to be equal, both iterable must return equal elements in the same order
     * and it isn't required that the two iterables are of the same type in order to be equal.
     * <p></p>
     * With this consideration, let's see how we can assert that
     * two lists of different types (LinkedList and ArrayList for example) are equal:
     */
    @Test
    void givenTwoLists_whenAssertingIterables_thenEquals() {
        Iterable<String> arrayList = new ArrayList<>(asList("Java", "Junit", "Test"));
        Iterable<String> linkedList = new LinkedList<>(asList("Java", "Junit", "Test"));

        // Asserts true
        assertIterableEquals(arrayList, linkedList);

        // 🔥 In the same way of the assertArrayEquals, if both iterables are null, they are considered equal.
    }
}
