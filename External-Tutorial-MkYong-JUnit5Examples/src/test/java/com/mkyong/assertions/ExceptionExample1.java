package com.mkyong.assertions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionExample1 {

    @Test
    void test_exception() {

        Exception exception = assertThrows(ArithmeticException.class, () -> divide(1, 0));

        assertEquals("/ by zero", exception.getMessage());

        assertTrue(exception.getMessage().contains("zero"));

    }

    int divide(int input, int divide) {
        return input / divide;
    }
}
