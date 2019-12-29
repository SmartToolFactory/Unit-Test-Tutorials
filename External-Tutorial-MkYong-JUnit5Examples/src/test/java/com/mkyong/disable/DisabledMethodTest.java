package com.mkyong.disable;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisabledMethodTest {

    @Disabled("Disabled until CustomerService is up!")
    @Test
    void testCustomerServiceGet() {
        assertEquals(2, 1 + 1);
    }

    @Test
    void test3Plus3() {
        assertEquals(6, 3 + 3);
    }

}
