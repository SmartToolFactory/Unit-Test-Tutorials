package com.smarttoolfactory.tutorial4_2kotlin_mockk

import com.smarttoolfactory.tutorial4_2kotlin_mockk.car.Car
import com.smarttoolfactory.tutorial4_2kotlin_mockk.car.Direction
import com.smarttoolfactory.tutorial4_2kotlin_mockk.car.Outcome
import io.mockk.*
import org.junit.jupiter.api.Test


/**
 * To exclude some not so important calls from being recorded you can use excludeRecords:
 *
 * excludeRecords { mock.operation(any(), 5) }
 *
 * All matching calls will be excluded from recording.
 * This may be useful in case you are using exhaustive verification:
 * verifyAll, verifySequence or confirmVerified.
 */
class Test13RecordingExclusions {

    @Test
    fun `Test exclusions`() {

        //Given
        val car = mockk<Car>()

        every { car.drive(Direction.NORTH) } returns Outcome.OK
        every { car.drive(Direction.SOUTH) } returns Outcome.OK

        excludeRecords { car.drive(Direction.SOUTH) }

        // When
        car.drive(Direction.NORTH) // returns OK
        car.drive(Direction.SOUTH) // returns OK

        // Then
        verify {
            car.drive(Direction.NORTH)
        }

        // car.drive(Direction.SOUTH) was excluded, so confirmation
        // is fine with only car.drive(Direction.NORTH)

        confirmVerified(car)

    }

    @Test
    fun `Test exclusions2`() {

        //Given
        val car = mockk<Car>()

        every { car.drive(any()) } returns Outcome.OK

        excludeRecords { car.drive(Direction.SOUTH) }

        // When
        car.drive(Direction.NORTH) // returns OK
        car.drive(Direction.SOUTH) // returns OK

        // Then
        verify {
            car.drive(Direction.NORTH)
        }

        // car.drive(Direction.SOUTH) was excluded, so confirmation
        // is fine with only car.drive(Direction.NORTH)

        confirmVerified(car)

    }
}