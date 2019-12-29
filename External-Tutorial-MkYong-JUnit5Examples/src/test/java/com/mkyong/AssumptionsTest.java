package com.mkyong;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

public class AssumptionsTest {

    // Output: org.opentest4j.TestAbortedException: Assumption failed: assumption is not true
    @DisplayName("Run this if `assumeTrue` condition is true, else aborting this test")
    @Test
    void testOnlyOnDevEnvElseAbort() {
        assumeTrue("DEV".equals(System.getenv("APP_MODE")));
        assertEquals(2, 1 + 1);
    }

    // Output: org.opentest4j.TestAbortedException: Assumption failed: Aborting test: not on developer environment
    @DisplayName("Run this if `assumeTrue` condition is true, else aborting this test (Custom Message)")
    @Test
    void testOnlyOnDevEnvElseAbortWithCustomMsg() {
        assumeTrue("DEV".equals(System.getenv("APP_MODE")), () -> "Aborting test: not on developer environment");
        assertEquals(2, 1 + 1);
    }

    @Test
    void testAssumingThat() {

        // run these assertions always, just like normal test
        assertEquals(2, 1 + 1);

        assumingThat("DEV".equals(System.getenv("APP_MODE")),
                () -> {
                    // run this only if assumingThat condition is true
                    assertEquals(2, 1 + 1);
                });

        // run these assertions always, just like normal test
        assertEquals(2, 1 + 1);

    }

}
