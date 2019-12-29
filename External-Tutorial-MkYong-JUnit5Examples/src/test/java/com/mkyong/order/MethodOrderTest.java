package com.mkyong.order;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MethodOrderTest {

    @Test
    void test0() {
        assertEquals(2, 1 + 1);
    }

    @Test
    @Order(3)
    void test1() {
        assertEquals(2, 1 + 1);
    }

    @Test
    @Order(1)
    void test2() {
        assertEquals(2, 1 + 1);
    }

    @Test
    @Order(2)
    void test3() {
        assertEquals(2, 1 + 1);
    }

    @Test
    void test4() {
        assertEquals(2, 1 + 1);
    }

}