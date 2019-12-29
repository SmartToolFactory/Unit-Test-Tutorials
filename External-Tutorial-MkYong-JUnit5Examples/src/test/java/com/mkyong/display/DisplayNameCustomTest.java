package com.mkyong.display;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("I'm a Test Class")
public class DisplayNameCustomTest {

    @Test
    @DisplayName("Test with spaces, expected ok")
    void test_spaces_ok() {
    }

    @DisplayName("Test with spaces, expected failed")
    @Test
    void test_spaces_fail() {
    }

}
