package com.mkyong.assertions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionExample2 {

    @Test
    void test_exception_custom() {
        Exception exception = assertThrows(NameNotFoundException.class, () -> findByName("mkyong"));
        assertTrue(exception.getMessage().contains("not found"));
    }

    String findByName(String name) throws NameNotFoundException{
        throw new NameNotFoundException( name + " not found!");
    }
}
