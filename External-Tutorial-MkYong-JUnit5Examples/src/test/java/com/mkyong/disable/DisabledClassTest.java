package com.mkyong.disable;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Disabled("Disabled until bug #2019 has been fixed!")
public class DisabledClassTest {

    @Test
    void test1Plus1() {
        assertEquals(2, 1 + 1);
    }

    @Test
    void test2Plus2() {
        assertEquals(4, 2 + 2);
    }

}
