package com.smarttoolfactory.tutorial3_1_junit5;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * DisplayName is displayed in test results or TestRunners.
 *
 * <p></p>
 * Disabled test are ignored.
 */
class Test2DisplayAndDisabled {


    @DisplayName("Single test successful")
    @Test
    void testSingleSuccessTest() {
        System.out.println("testSingleSuccessTest is run");
    }

    @Test
    @Disabled("😭 Not implemented yet")
    void testShowSomething() {
        System.out.println("testShowSomething should not display this since it's to be IGNORED");

    }
}
