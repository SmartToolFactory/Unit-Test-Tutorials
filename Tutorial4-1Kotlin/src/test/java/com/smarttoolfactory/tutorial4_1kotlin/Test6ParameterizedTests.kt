package com.smarttoolfactory.tutorial4_1kotlin

import com.smarttoolfactory.tutorial4_1kotlin.model_person.Person
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.*
import org.junit.jupiter.params.provider.Arguments.arguments
import java.time.LocalDate
import java.time.Month
import java.util.*
import java.util.stream.Stream

class Test6ParameterizedTests {

    /*
     * @ValueSource
     * ValueSource test take arguments inside this annotation and can be used as parameters.
     */
    @ParameterizedTest
    @ValueSource(strings = ["cali", "bali", "dani"])
    fun `Ends with i`(str: String) {
        println("Test with param: $str")
        assertTrue(str.endsWith("i"))
    }


    @ParameterizedTest
    @ValueSource(ints = [1, 3, 5, 7])
    fun `Is an odd number`(num: Int) {

        assertTrue(num % 2 == 1)
    }

    @ParameterizedTest
    @ValueSource(strings = ["2002-01-23", "1956-03-14", "1503-07-19"])
    fun `Check date in past`(date: LocalDate) {
        assertTrue(date.isBefore(LocalDate.now()))
    }

    /*
     * @EnumSource
     * In order to run a test with different values from an enumeration,
     * we can use the @EnumSource annotation.
     */

    @ParameterizedTest
    @EnumSource(Month::class)
    fun `Months are between 1 and 12`(month: Month) {

        val monthNumber = month.value
        assertTrue(monthNumber in 1..12)
    }

    /**
     * By default, the names will only keep the matched enum values.
     * We can turn this around by setting the mode attribute to EXCLUDE:
     *
     * @param month
     */
    @ParameterizedTest
    @EnumSource(
        value = Month::class,
        names = ["APRIL", "JUNE", "SEPTEMBER", "NOVEMBER", "FEBRUARY"],
        mode = EnumSource.Mode.EXCLUDE
    )
    fun `Months which have 31 days`(month: Month) {
        val isALeapYear = false
        println("Enum test month: $month")
        assertEquals(31, month.length(isALeapYear))
    }

    /**
     * we can pass a regular expression to the names attribute
     *
     * @param month
     */
    @ParameterizedTest
    @EnumSource(value = Month::class, names = [".+BER"], mode = EnumSource.Mode.MATCH_ANY)
    fun `Months ending with ber`(month: Month?) {
        val months = EnumSet.of(
            Month.SEPTEMBER,
            Month.OCTOBER,
            Month.NOVEMBER,
            Month.DECEMBER
        )
        assertTrue(months.contains(month))
    }

    /**
     * Quite similar to @ValueSource, @EnumSource is only applicable when we're going
     * to pass just one argument per test execution.
     *
     * @param month
     */
    @ParameterizedTest
    @EnumSource(value = Month::class, names = [".+BER"], mode = EnumSource.Mode.MATCH_ANY)
    fun `Months ending with ber check with EnumSource`(month: Month?) {
        val months = EnumSet.of(
            Month.SEPTEMBER,
            Month.OCTOBER,
            Month.NOVEMBER,
            Month.DECEMBER
        )
        assertTrue(months.contains(month))
    }

    enum class Size {
        XXS, XS, S, M, L, XL, XXL, XXXL
    }

    @ParameterizedTest
    @EnumSource(Size::class)
    fun `Sizes are not null`(size: Size?) {
        Assertions.assertNotNull(size)
    }

    @ParameterizedTest(name = "#{index} - Is size contains {0}?")
    @EnumSource(value = Size::class, names = ["L", "XL", "XXL", "XXXL"])
    fun `Sizes only include enums`(size: Size?) {
        assertTrue(
            EnumSet.allOf(Size::class.java).contains(
                size
            )
        )
    }

    // Size = M, L, XL, XXL, XXXL
    @ParameterizedTest
    @EnumSource(value = Size::class, mode = EnumSource.Mode.EXCLUDE, names = ["XXS", "XS", "S"])
    fun `Sizes do not contain XXXL`(size: Size?) {
        val excludeSmallSize = EnumSet.range(Size.M, Size.XXXL)
        assertTrue(excludeSmallSize.contains(size))
    }

    /*
    * @MethodSource
    * MethodSource annotation generates parameters using methods with a name or the same name
    * of the test method used.
    *
    * 🔥The @MethodSource annotation allows us to produce test parameters by calling
    * a static function that resides in the same class as the test.
    * This is possible but not obvious in Kotlin. We have to use the
    * @JvmStatic annotation inside a companion object:
    *
    * 🔥🔥 This also means that the methods used to produce parameters must all be together
    * since we can only have a single companion object per class.
    */


    /**
     * With [List]
     */
    @ParameterizedTest
    @MethodSource("intProvider")
    fun `Test with custom arguments provider`(argument: Int) {
        assertNotNull(argument)
    }

    /**
     * With [Stream]
     */
    @ParameterizedTest
    @MethodSource("squares")
    fun `Square of the input`(input: Int, expected: Int) {
        assertEquals(expected, input * input)
    }

    @ParameterizedTest
    @MethodSource("stringIntAndListProvider")
    fun `Multiple arugments with MethodSource`(
        str: String,
        num: Int,
        list: List<String?>
    ) {
        assertEquals(5, str.length)
        assertTrue(num in 1..2)
        assertEquals(2, list.size)
    }


    companion object {

        /**
         * With [List]
         */
        @JvmStatic
        fun squares() = listOf(
            Arguments.of(1, 1),
            Arguments.of(2, 4)
        )

        /**
         * With [Stream]
         */
        @JvmStatic
        fun intProvider(): Stream<Int> = Stream.of(0, 42, 9000)


        /**
         * With [Stream] and [listOf] as parameter
         */
        @JvmStatic
        fun stringIntAndListProvider(): Stream<Arguments?>? {
            return Stream.of(
                arguments("apple", 1, listOf("a", "b")),
                arguments("lemon", 2, listOf("x", "y"))
            )
        }
    }

    /*
     *  @CsvSource
     */
    @ParameterizedTest
    @CsvSource(
        "1, 1",
        "2, 4",
        "3, 9"
    )
    fun `Squares test with CsvSource`(input: Int, expected: Int) {
        assertEquals(expected, input * input)
    }

    /*
     * @ArgumentSource
     */
    @ParameterizedTest
    @ArgumentsSource(PersonProvider::class)
    fun `Check age greater or equals 18`(student: Person) {
        assertTrue(student.age >= 18)
    }

    internal class PersonProvider : ArgumentsProvider {

        override fun provideArguments(context: ExtensionContext): Stream<out Arguments> = Stream.of(
            Person("John", "Doe", LocalDate.of(1969, 5, 20)),
            Person("Jane", "Smith", LocalDate.of(1997, 11, 21)),
            Person("Ivan", "Ivanov", LocalDate.of(1994, 2, 12))
        ).map { Arguments.of(it) }
    }


}