package com.mkyong.params;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvSourceTest {

    @ParameterizedTest
    @CsvSource({
            "java,      4",
            "clojure,   7",
            "python,    6"
    })
    void test_Csv(String str, int length) {
        assertEquals(length, str.length());
    }

}
