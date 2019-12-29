package com.mkyong.display;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@DisplayNameGeneration(DisplayNameGenerator2Test.CustomDisplayNameGenerator.class)
public class DisplayNameGenerator2Test {

    @Test
    void test_spaces_ok() {
    }

    @Test
    void test_spaces_fail() {
    }

    static class CustomDisplayNameGenerator extends DisplayNameGenerator.Standard {

        @Override
        public String generateDisplayNameForClass(Class<?> testClass) {
            return "New Name for test class";
        }

        @Override
        public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
            return super.generateDisplayNameForNestedClass(nestedClass);
        }
        
        @Override
        public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
            String name = testMethod.getName();
            return Arrays.stream(name.split("_")).collect(Collectors.joining(" | "));
        }
    }

}
