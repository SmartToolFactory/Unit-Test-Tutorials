package com.mkyong.display;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class DisplayNameGenerator1Test {

    @Test
    void test_spaces_ok() {
    }

    @Test
    void test_spaces_fail() {
    }

}
