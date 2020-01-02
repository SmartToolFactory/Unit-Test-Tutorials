package com.smarttoolfactory.tutorial4_1kotlin

import com.smarttoolfactory.tutorial4_1kotlin.model_person.Person
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.time.LocalDate
import java.util.stream.Stream

class Test7NestedTests2 {

    @Nested
    inner class `Check age of person` {

        @ParameterizedTest
        @ArgumentsSource(PersonProvider::class)
        fun `Check age greater or equals 18`(student: Person) {
            assertTrue(student.age >= 18)
        }

        @ParameterizedTest
        @ArgumentsSource(PersonProvider::class)
        fun `Check birth date is after 1950`(student: Person) {
            assertTrue(LocalDate.of(1950, 12, 31).isBefore(student.birthDate))
        }

    }

    @Nested
    inner class `Check name of person` {

        @ParameterizedTest
        @ArgumentsSource(PersonProvider::class)
        fun `Check first name length is 4`(student: Person) {
            assertEquals(4, student.firstName.length)
        }
    }

    internal class PersonProvider : ArgumentsProvider {

        override fun provideArguments(context: ExtensionContext): Stream<out Arguments> = Stream.of(
            Person("John", "Doe", LocalDate.of(1969, 5, 20)),
            Person("Jane", "Smith", LocalDate.of(1997, 11, 21)),
            Person("Ivan", "Ivanov", LocalDate.of(1994, 2, 12))
        ).map { Arguments.of(it) }
    }

}