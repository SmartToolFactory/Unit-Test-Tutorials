package com.mkyong.display;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DisplayNameParamTest {

    @ParameterizedTest(name = "#{index} - Test with TimeUnit: {0}")
    @EnumSource(value = TimeUnit.class, names = {"MINUTES", "SECONDS"})
    void test_timeunit_ok(TimeUnit time) {
    }


    @ParameterizedTest(name = "#{index} - Test with {0} and {1}")
    @MethodSource("argumentProvider")
    void test_method_multi(String str, int length) {
    }

    static Stream<Arguments> argumentProvider() {
        return Stream.of(
                arguments("abc", 3),
                arguments("lemon", 2)
        );
    }

}
