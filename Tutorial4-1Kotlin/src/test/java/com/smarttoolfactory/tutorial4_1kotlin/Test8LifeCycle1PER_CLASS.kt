package com.smarttoolfactory.tutorial4_1kotlin

import com.smarttoolfactory.tutorial4_1kotlin.model_calculation.Calculation
import org.junit.jupiter.api.*


/**
 * * [TestInstance.Lifecycle.PER_CLASS] lets static @BeforeAll and @AfterAll to be invoked
 * without @JvmStatic annotation and.
 *
 * * @BeforeEach and @AfterEach is called for same instance of the test class
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class Test8LifeCycle1PER_CLASS {

    /*
        Prints:
        before all: Test8LifeCycle1@670b40af
        before each: Test8LifeCycle1@670b40af
        Test case cube: Test8LifeCycle1@670b40af
        after each: Test8LifeCycle1@670b40af
        before each: Test8LifeCycle1@670b40af
        Test Initial Test8LifeCycle1@670b40af
        after each: Test8LifeCycle1@670b40af
        after all: Test8LifeCycle1@670b40af
     */

    private val calculation: Calculation = Calculation()

    @BeforeAll
    fun setUpBeforeClass() {
        println("before all: $this")
    }

    /**
     * method with BeforeEach annotation is invoked after each test
     */
    @BeforeEach
    fun setUp() {
        println("before each: $this")
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

    @AfterAll
    fun tearDownAfterClass() {
        println("after all: $this")
    }

    override fun toString(): String {
        return javaClass.simpleName + "@" + Integer.toHexString(hashCode())
    }
}
