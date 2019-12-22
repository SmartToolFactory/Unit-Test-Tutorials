package com.smarttoolfactory.tutorial3_1_junit5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test4Parameterized {

    @ParameterizedTest
    @ValueSource(strings = {"cali", "bali", "dani"})
    void endsWithI(String str) {

        System.out.println("Test with param: " + str);
        assertTrue(str.endsWith("i"));

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE})
    void isOdd_ShouldReturnTrueForOddNumbers(int number) {

        System.out.println("isOdd_ShouldReturnTrueForOddNumbers param: " + number);
        int expected = number % 2;
        assertThat(expected, is(not(0)));
        // 🔥 This is 1 OR -1 with assertThat
        assertThat(expected, anyOf(is(1), is(-1)));

    }
}
