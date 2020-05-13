package com.smarttoolfactory.tutorial4_2kotlin_mockk

import com.google.common.truth.Truth
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class Test16Coroutines {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()


    /**
     * This test passes but it has to WAIT the delay of suspending function
     */
    @Test
    fun `First Coroutine test`() = runBlocking {

        println("Test started")

        // GIVEN
        val expected = "Hello World"

        // WHEN
        val actual = GlobalScope.async {
            mockSomeDelayedWork()
        }

        // THEN
        Truth.assertThat(actual.await()).isEqualTo(expected)
        println("Test finished")

    }


    /**
        This test FAILS because it's not [TestCoroutineDispatcher]
        that calling [TestCoroutineDispatcher.runBlockingTest]
     */
    @Test
    fun `Coroutine test with runBlockingTest that fails`() = runBlockingTest {

        println("Test started")

        println("Test thread: ${Thread.currentThread().name}, scope: $this")

        // GIVEN
        val expected = "Hello World"

        // WHEN
        val actual = withContext(testCoroutineDispatcher) {
            println("Test Dispatcher thread: ${Thread.currentThread().name}, scope: $this")

            async {
                println("Inside async thread: ${Thread.currentThread().name}, scope: $this")

                mockSomeDelayedWork()
            }
        }

        // THEN
        Truth.assertThat(actual.await()).isEqualTo(expected)
        println("Test finished")

        /*
            Prints:
            Test started
            Test thread: main @coroutine#1, scope: kotlinx.coroutines.test.TestCoroutineScopeImpl@157632c9
            Test Dispatcher thread: main @coroutine#1, scope: "coroutine#1":DispatchedCoroutine{Active}@67e2d983
            Inside async thread: main @coroutine#2, scope: "coroutine#2":DeferredCoroutine{Active}@5d47c63f
         */

    }

    @Test
    fun `Coroutine test with TestCoroutineDispatcher`() = testCoroutineDispatcher.runBlockingTest {

        println("Test started")

        println("Test thread: ${Thread.currentThread().name}, scope: $this")

        // GIVEN
        val expected = "Hello World"

        // WHEN
        val actual = withContext(testCoroutineDispatcher) {
            println("Test Dispatcher thread: ${Thread.currentThread().name}, scope: $this")

            async {
                println("Inside async thread: ${Thread.currentThread().name}, scope: $this")

                mockSomeDelayedWork()
            }
        }

        // THEN
        Truth.assertThat(actual.await()).isEqualTo(expected)
        println("Test finished")

        /*
            Prints:

            Test started
            Test thread: main @coroutine#1, scope: kotlinx.coroutines.test.TestCoroutineScopeImpl@157632c9
            Test Dispatcher thread: main @coroutine#1, scope: "coroutine#1":UndispatchedCoroutine{Active}@3bbc39f8
            Inside async thread: main @coroutine#2, scope: "coroutine#2":DeferredCoroutine{Active}@4ae3c1cd
            Test finished
         */

    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        try {
            testCoroutineDispatcher.cleanupTestCoroutines()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}


private suspend fun mockSomeDelayedWork(): String {

    delay(5_000)
    return "Hello World"

}

