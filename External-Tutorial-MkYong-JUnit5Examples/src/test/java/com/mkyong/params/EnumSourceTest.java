package com.mkyong.params;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

public class EnumSourceTest {

    enum Size {
        XXS, XS, S, M, L, XL, XXL, XXXL;
    }

    @ParameterizedTest
    @EnumSource(Size.class)
    void test_enum(Size size) {
        assertNotNull(size);
    }

    // Size = L, XL, XXL , XXXL
    @ParameterizedTest(name = "#{index} - Is size contains {0}?")
    @EnumSource(value = Size.class, names = {"L", "XL", "XXL", "XXXL"})
    void test_enum_include(Size size) {
        assertTrue(EnumSet.allOf(Size.class).contains(size));
    }

    // Size = M, L, XL, XXL, XXXL
    @ParameterizedTest
    @EnumSource(value = Size.class, mode = EXCLUDE, names = {"XXS", "XS", "S"})
    void test_enum_exclude(Size size) {
        EnumSet<Size> excludeSmallSize = EnumSet.range(Size.M, Size.XXXL);
        assertTrue(excludeSmallSize.contains(size));
    }

}