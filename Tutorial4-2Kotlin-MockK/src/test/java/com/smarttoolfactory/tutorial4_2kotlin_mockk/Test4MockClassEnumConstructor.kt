package com.smarttoolfactory.tutorial4_2kotlin_mockk

import com.smarttoolfactory.tutorial4_2kotlin_mockk.model_car.Car
import com.smarttoolfactory.tutorial4_2kotlin_mockk.model_car.Direction
import com.smarttoolfactory.tutorial4_2kotlin_mockk.model_car.Outcome
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Test4MockClassEnumConstructor {

    @Nested
    inner class Test4MockClass {

        private val car = mockkClass(Car::class)


        @Test
        fun `Class mock test`() {

            // Given
            every { car.drive(Direction.NORTH) } returns Outcome.OK

            // When
            car.drive(Direction.NORTH) // returns OK

            // Then
            verify { car.drive(Direction.NORTH) }
        }
    }

    /**
     * Enums can be mocked using mockkObject
     */
    @Nested
    inner class Test4MockEnum {

        @Test
        fun `Enum mock test`() {

            // Given
            mockkObject(Enumeration.CONSTANT)
            every { Enumeration.CONSTANT.goodInt } returns 42

            // Then
            assertEquals(42, Enumeration.CONSTANT.goodInt)
        }

    }

    // TODO Finish this example
    @Nested
    inner class Test4MockConstructor {

    }

}

enum class Enumeration(val goodInt: Int) {
    CONSTANT(35),
    OTHER_CONSTANT(45);
}
