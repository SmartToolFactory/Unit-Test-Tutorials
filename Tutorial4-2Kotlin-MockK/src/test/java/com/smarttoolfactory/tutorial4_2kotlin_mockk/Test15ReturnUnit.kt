package com.smarttoolfactory.tutorial4_2kotlin_mockk

import io.mockk.*
import org.junit.jupiter.api.Test

class Test15ReturnUnit {

    class MockedClass {
        fun sum(a: Int, b: Int): Unit {
            println(a + b)
        }
    }

    @Test
    fun `Test unit function`() {

        val obj = mockk<MockedClass>()

        every { obj.sum(any(), any()) } just Runs

        obj.sum(1, 1)
        obj.sum(1, 2)
        obj.sum(1, 3)

        verify {
            obj.sum(1, 1)
            obj.sum(1, 2)
            obj.sum(1, 3)
        }

    }


}