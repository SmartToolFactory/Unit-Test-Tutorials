package com.smarttoolfactory.tutorial6_1testdrivendevelopment

import org.junit.jupiter.api.Assertions

infix fun Any.assertThatEquals(any: Any) {
    Assertions.assertEquals(this, any)
}

infix fun Any.assertThatNotEquals(any: Any) {
    Assertions.assertNotEquals(this, any)
}