package com.mkyong.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(ParameterCountOrder.class)
public class MethodParameterCountTest {

    @DisplayName("Parameter Count : 2")
    @ParameterizedTest(name = "{index} ==> fruit=''{0}'', qty={1}")
    @CsvSource({
            "apple,         1",
            "banana,        2"
    })
    void test2(String fruit, int qty) {
        assertTrue(true);
    }

    @DisplayName("Parameter Count : 1")
    @ParameterizedTest(name = "{index} ==> ints={0}")
    @ValueSource(ints = {1, 2, 3})
    void test1(int num1) {
        assertTrue(num1 < 4);
    }

    @DisplayName("Parameter Count : 3")
    @ParameterizedTest(name = "{index} ==> fruit=''{0}'', qty={1}, price={2}")
    @CsvSource({
            "apple,         1,  1.99",
            "banana,        2,  2.99"
    })
    void test3(String fruit, int qty, BigDecimal price) {
        assertTrue(true);
    }

}
