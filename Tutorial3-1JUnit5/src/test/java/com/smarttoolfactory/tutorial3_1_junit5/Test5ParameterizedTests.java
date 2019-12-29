package com.smarttoolfactory.tutorial3_1_junit5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;

import java.time.Month;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

class Test5ParameterizedTests {

    /*
        @ValueSource
        @ValueSource is one of the simplest possible sources.
        It lets you specify a single array of literal values and can only
        be used for providing a single argument per parameterized test invocation.

        The following types of literal values are supported by @ValueSource.

        short

        byte

        int

        long

        float

        double

        char

        boolean

        java.lang.String

        java.lang.Class
     */
    @ParameterizedTest
    @ValueSource(strings = {"cali", "bali", "dani"})
    void endsWithI(String str) {

        System.out.println("Test with param: " + str);
        assertTrue(str.endsWith("i"));

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE})
    void isOdd_ShouldReturnTrueForOddNumbers(int number) {

        System.out.println("isOdd_ShouldReturnTrueForOddNumbers param: " + number);
        int expected = number % 2;
        assertThat(expected, is(not(0)));
        // 🔥 This is 1 OR -1 with assertThat
        assertThat(expected, anyOf(is(1), is(-1)));

    }


    /*
        EnumSource
        In order to run a test with different values from an enumeration,
        we can use the @EnumSource annotation.
     */

    @ParameterizedTest
    @EnumSource(Month.class)
        // passing all 12 months
    void getValueForAMonth_IsAlwaysBetweenOneAndTwelve(Month month) {
        int monthNumber = month.getValue();
        System.out.println("Enum test: " + monthNumber);
        assertTrue(monthNumber >= 1 && monthNumber <= 12);
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
    void someMonths_Are30DaysLong(Month month) {
        final boolean isALeapYear = false;
        System.out.println("Enum test month: " + month);
        assertEquals(30, month.length(isALeapYear));
    }

    /**
     * By default, the names will only keep the matched enum values.
     * We can turn this around by setting the mode attribute to EXCLUDE:
     *
     * @param month
     */
    @ParameterizedTest
    @EnumSource(
            value = Month.class,
            names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER", "FEBRUARY"},
            mode = EXCLUDE)
    void exceptFourMonths_OthersAre31DaysLong(Month month) {
        final boolean isALeapYear = false;
        System.out.println("Enum test month: " + month);
        assertEquals(31, month.length(isALeapYear));
    }


    /**
     * we can pass a regular expression to the names attribute
     *
     * @param month
     */
    @ParameterizedTest
    @EnumSource(value = Month.class, names = ".+BER", mode = EnumSource.Mode.MATCH_ANY)
    void fourMonths_AreEndingWithBer(Month month) {
        EnumSet<Month> months =
                EnumSet.of(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
        assertTrue(months.contains(month));
    }


    /**
     * Quite similar to @ValueSource, @EnumSource is only applicable when we're going
     * to pass just one argument per test execution.
     *
     * @param month
     */
    @ParameterizedTest
    @EnumSource(value = Month.class, names = ".+BER", mode = EnumSource.Mode.MATCH_ANY)
    void fourMonths_AreEndingWithBer2(Month month) {
        EnumSet<Month> months =
                EnumSet.of(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
        assertTrue(months.contains(month));
    }

    enum Size {
        XXS, XS, S, M, L, XL, XXL, XXXL;
    }

    @ParameterizedTest
    @EnumSource(Size.class)
    void test_enum(Size size) {
        assertNotNull(size);
    }

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

    /*
        CSV Literals
     */

    /**
     * Suppose we're going to make sure that the toUpperCase() method from String
     * generates the expected uppercase value. @ValueSource won't be enough.
     * <p>
     * In order to write a parameterized test for such scenarios, we have to:
     * <p>
     * Pass an input value and an expected value to the test method
     * Compute the actual result with those input values
     * Assert the actual value with the expected value
     */

    @ParameterizedTest
    @CsvSource({"test,TEST", "tEst,TEST", "Java,JAVA"})
    void toUpperCase_ShouldGenerateTheExpectedUppercaseValue(String input, String expected) {
        System.out.println("Test input: " + input + ", expected: " + expected);
        String actualValue = input.toUpperCase();
        assertEquals(expected, actualValue);
    }


    /**
     * Delimeter is : instead of ,
     *
     * @param input
     * @param expected
     */
    @ParameterizedTest
    @CsvSource(value = {"test:test", "tEst:test", "Java:java"}, delimiter = ':')
    void toLowerCase_ShouldGenerateTheExpectedLowercaseValue(String input, String expected) {
        String actualValue = input.toLowerCase();
        assertEquals(expected, actualValue);
    }

    /*
        Method Source
     */

    /**
     * Uses arguments of provideStringsForIsBlank
     *
     * @param input
     * @param expected
     */
    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input, boolean expected) {

        System.out.println("Input: " + input + ", expected: " + expected);
        assertEquals(expected, StringUtils.isBlank(input));
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of("  ", true),
                Arguments.of("not blank", false)
        );
    }

    @ParameterizedTest
    @MethodSource
        // hmm, no method name ...
    void isBlank_ShouldReturnTrueForNullOrBlankStringsOneArgument(String input) {
        assertTrue(StringUtils.isBlank(input));
    }

    private static Stream<String> isBlank_ShouldReturnTrueForNullOrBlankStringsOneArgument() {
        return Stream.of(null, "", "  ");
    }

    /**
     * Sometimes it's useful to share arguments between different test classes.
     * In these cases, we can refer to a source method outside of the current
     * class by its fully-qualified name:
     */
    @ParameterizedTest
    @MethodSource("com.smarttoolfactory.tutorial3_1_junit5.StringParams#blankStrings")
    void isBlank_ShouldReturnTrueForNullOrBlankStringsExternalSource(String input) {
        System.out.println("Test Arguments: " + input);
        assertTrue(StringUtils.isBlank(input));
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    void testWithExplicitLocalMethodSource(String argument) {
        assertNotNull(argument);
    }

    static Stream<String> stringProvider() {
        return Stream.of("apple", "banana");
    }

    @ParameterizedTest
    @MethodSource("range")
    void testWithRangeMethodSource(int argument) {
        assertNotEquals(9, argument);
    }

    /**
     * Streams for primitive types (DoubleStream, IntStream, and LongStream)
     * are also supported as demonstrated by the following example.
     * @return
     */
    static IntStream range() {
        return IntStream.range(0, 20).skip(10);
    }


    @ParameterizedTest
    @MethodSource("stringIntAndListProvider")
    void testWithMultiArgMethodSource(String str, int num, List<String> list) {
        assertEquals(5, str.length());
        assertTrue(num >=1 && num <=2);
        assertEquals(2, list.size());
    }


    /**
     * If a parameterized test method declares multiple parameters,
     * you need to return a collection, stream, or array of Arguments
     * instances or object arrays as shown below
     * (see the Javadoc for @MethodSource for further details on supported return types).
     * Note that arguments(Object…​) is a static factory
     * method defined in the Arguments interface. In addition,
     * Arguments.of(Object…​) may be used as an alternative to arguments(Object…​).
     * @return
     */
    static Stream<Arguments> stringIntAndListProvider() {
        return Stream.of(
                arguments("apple", 1, Arrays.asList("a", "b")),
                arguments("lemon", 2, Arrays.asList("x", "y"))
        );
    }
}


