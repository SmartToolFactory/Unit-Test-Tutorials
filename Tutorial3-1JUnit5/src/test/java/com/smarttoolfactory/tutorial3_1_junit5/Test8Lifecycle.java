package com.smarttoolfactory.tutorial3_1_junit5;

import androidx.annotation.NonNull;

import com.smarttoolfactory.tutorial3_1_junit5.model_calculation.Calculation;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * By default, both JUnit 4 and 5 create a new instance of the test class before running each test method.
 * This provides a clean separation of state between tests. With <strong>TestInstance.Lifecycle.PER_CLASS</strong>
 * annotation same instance of this Test class is used
 *
 * <p></p>
 * With TestInstance.Lifecycle.PER_CLASS @BeforeAll, and @AfterAll methods can be non-static
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Test8Lifecycle {


    Calculation mCalculation;

    @BeforeAll
    void setUpBeforeClass() throws Exception {
        System.out.println("before all: " + this);

    }

    /**
     * method with BeforeEach annotation is invoked after each test
     */
    @BeforeEach
    void setUp() throws Exception {
        mCalculation = new Calculation();
        System.out.println("before each: " + this);
    }

    @Test
    void testInitial() {
        System.out.println("Test Initial " + this);
        assertEquals("Hello", new String("Hello"));
        assertEquals(new String("Hello"), new String("Hello"));
    }

    @Test
    void testCube() {
        System.out.println("Test case cube: " + this);
        assertEquals(27, mCalculation.cube(3));
    }


    /**
     * method with AfterEach annotation is invoked after each test
     */
    @AfterEach
    void tearDown() throws Exception {
        System.out.println("after each: " + this);
    }

    @AfterAll
    void tearDownAfterClass() throws Exception {
        System.out.println("after all: " + this);
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());

    }
}
