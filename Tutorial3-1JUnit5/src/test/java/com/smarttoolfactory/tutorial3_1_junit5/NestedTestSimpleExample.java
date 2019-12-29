package com.smarttoolfactory.tutorial3_1_junit5;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NestedTestSimpleExample {

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("@BeforeAll - Outer Class");
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        System.out.println("@AfterAll - Outer Class");
    }

    @BeforeEach
    void setUp() throws Exception {
        System.out.println("@BeforeEach - Outer Class");
    }

    @AfterEach
    void tearDown() throws Exception {
        System.out.println("@AfterEach - Outer Class");
    }

    @Test
    void test1Outer() {
        System.out.println("Outer Class Test1");
    }

    @Test
    void test2Outer() {
        System.out.println("Outer Class Test2");
    }


    @Nested
    class InnerClass {
        @BeforeEach
        void setUp() throws Exception {
            System.out.println("@BeforeEach - Inner Class");
        }

        @AfterEach
        void tearDown() throws Exception {
            System.out.println("@AfterEach - Inner Class");
        }

        @Test
        void test1Inner() {
            System.out.println("Inner Class Test1");
        }

        @Test
        void test2Inner() {
            System.out.println("Inner Class Test2");
        }
    }
}