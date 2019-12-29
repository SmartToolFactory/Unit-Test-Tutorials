package com.mkyong.params;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValueSourceEmptyTest {

    boolean isEmpty(String input) {
        return (input == null || input.length() == 0);
    }

    // run 3 times, 1 for empty, 1 for null, 1 for ""
    @ParameterizedTest(name = "#{index} - isEmpty()? {0}")
    @EmptySource
    @NullSource
    //@NullAndEmptySource
    @ValueSource(strings = {""})
    void test_is_empty_true(String arg) {
        assertTrue(isEmpty(arg));
    }

    @ParameterizedTest(name = "#{index} - isEmpty()? {0}")
    @ValueSource(strings = {" ", "\n", "a", "\t"})
    void test_is_empty_false(String arg) {
        assertFalse(isEmpty(arg));
    }

}