package com.smarttoolfactory.tutorial3_1_junit5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;


/**
 * Assumptions are used to run tests only if certain conditions are met.
 * <p>
 * This is typically used for external conditions that are required for the test to run properly,
 * but which are not directly related to whatever is being tested.
 * <p>
 * You can declare an assumption with assumeTrue(), assumeFalse(), and assumingThat().
 *
 * <p></p>
 * If an assumption FAILS, a <strong>TestAbortedException</strong> is thrown and the test is simply skipped.
 * <p></p>
 * Assumptions also understand lambda expressions.
 */
class Test5Assumptions {

    @Test
    void testOnDev() {
        System.setProperty("ENV", "DEV");
        assumeTrue("DEV".equals(System.getProperty("ENV")));
        //remainder of test will proceed
    }

    @Test
    void testOnProd() {
        System.setProperty("ENV", "PROD");
        assumeTrue("DEV".equals(System.getProperty("ENV")), Test5Assumptions::message);
        //remainder of test will be aborted
    }

    private static String message() {
        return "TEST Execution Failed :: ";
    }

    @Test
    void trueAssumption() {
        assumeTrue(5 > 1);
        assertEquals(5 + 2, 7);
    }

    @Test
    void falseAssumption() {
        assumeFalse(5 < 1);
        assertEquals(5 + 2, 7);
    }

    @Test
    void assumptionThat() {

        String someString = "Just a string";

        assumingThat(
                someString.equals("Just a string"),
                () -> assertEquals(2 + 2, 4)
        );
    }

}
