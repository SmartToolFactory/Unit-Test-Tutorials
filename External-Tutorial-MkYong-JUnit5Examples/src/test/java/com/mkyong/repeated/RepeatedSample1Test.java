package com.mkyong.repeated;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepeatedSample1Test {

    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("afterAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("afterEach");
    }

    // Repeat this test 3 times
    @RepeatedTest(3)
    void math_add_1() {
        System.out.println("Run math_add_1()");
        assertEquals(2, 1 + 1);
    }

    @RepeatedTest(3)
    void math_add_2() {
        System.out.println("Run math_add_2()");
        assertEquals(2, 1 + 1);
    }

}