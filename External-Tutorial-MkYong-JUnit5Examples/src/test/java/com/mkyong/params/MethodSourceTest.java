package com.mkyong.params;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MethodSourceTest {

    @ParameterizedTest(name = "#{index} - Test with String : {0}")
    @MethodSource("stringProvider")
    void test_method_string(String arg) {
        assertNotNull(arg);
    }

    // this need static
    static Stream<String> stringProvider() {
        return Stream.of("java", "rust");
    }

    @ParameterizedTest(name = "#{index} - Test with Int : {0}")
    @MethodSource("rangeProvider")
    void test_method_int(int arg) {
        assertTrue(arg < 10);
    }

    // this need static
    static IntStream rangeProvider() {
        return IntStream.range(0, 10);
    }

}
