package com.smarttoolfactory.tutorial4_1kotlin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Test2Assertions2Suppliers {

    /**
     * Test with BooleanSupplier
     */
    @Test
    fun `isEmpty should return true for empty lists`() {
        val list = listOf<String>()
        assertTrue(list::isEmpty)
    }
}