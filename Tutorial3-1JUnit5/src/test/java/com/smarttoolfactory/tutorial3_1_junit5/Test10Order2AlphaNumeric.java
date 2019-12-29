package com.smarttoolfactory.tutorial3_1_junit5;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Tests run in order with the name given to methods based on alphanumeric ordering
 */
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class Test10Order2AlphaNumeric {

    @Test
    void testC() {
        System.out.println("testC");
    }

    @Test
    void testD() {
        System.out.println("testD");
    }

    @Test
    void testA() {
        System.out.println("testA");
    }

    @Test
    void testE() {
        System.out.println("testE");
    }

    @Test
    void testB() {
        System.out.println("testB");
    }

    /*
        Prints
        testA
        testB
        testC
        testD
        testE
     */

}
