package com.smarttoolfactory.tutorial4_1kotlin

import com.smarttoolfactory.tutorial4_1kotlin.model_calculation.Calculation
import org.junit.jupiter.api.*


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
internal class Test8LifeCycle1PER_METHOD {


    private lateinit var calculation: Calculation


    companion object {

        @BeforeAll
        @JvmStatic
        fun setUpBeforeClass() {
            println("before all: $this")
        }

        @AfterAll
        @JvmStatic
        fun tearDownAfterClass() {
            println("after all: $this")
        }

    }

    /**
     * method with BeforeEach annotation is invoked after each test
     */
    @BeforeEach
    fun setUp() {
        calculation = Calculation()

        println("before each: $this, calculation: $calculation")

    }

    @Test
    fun testInitial() {
        println("Test Initial $this")
        Assertions.assertEquals("Hello", "Hello")

    }

    @Test
    fun testCube() {
        println("Test case cube: $this")
        Assertions.assertEquals(27, calculation.cube(3))
    }

    /**
     * method with AfterEach annotation is invoked after each test
     */
    @AfterEach
    fun tearDown() {
        println("after each: $this")
    }


    override fun toString(): String {
        return javaClass.simpleName + "@" + Integer.toHexString(hashCode())
    }
}
