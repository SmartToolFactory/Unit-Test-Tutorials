package com.smarttoolfactory.tutorial3_1_junit5;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Test9Order1OrderAnnotation {


    @Test
    @Order(4)
    void test4() {
        System.out.println("Test4");
    }

    @Test
    @Order(5)
    void test5() {
        System.out.println("Test5");
    }

    @Test
    @Order(1)
    void test1() {
        System.out.println("Test1");
    }

    @Test
    @Order(2)
    void test2() {
        System.out.println("Test2");
    }

    @Test
    @Order(3)
    void test3() {
        System.out.println("Test3");
    }




}
