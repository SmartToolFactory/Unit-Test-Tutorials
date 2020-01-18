package com.smarttoolfactory.tutorial6_1testdrivendevelopment

import org.junit.jupiter.api.Assertions

infix fun Any.assertEquals(any: Any) {
    Assertions.assertEquals(this, any)
}

infix fun Any.assertNotEquals(any: Any) {
    Assertions.assertNotEquals(this, any)
}