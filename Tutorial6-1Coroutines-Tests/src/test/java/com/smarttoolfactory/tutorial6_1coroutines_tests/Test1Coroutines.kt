package com.smarttoolfactory.tutorial6_1coroutines_tests

import com.google.common.truth.Truth
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class Test16Coroutines {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    /**
     * This test passes but it has to WAIT the delay of suspending function.
     *
     * * [runBlockingTest] has a dispatcher that calls ` dispatcher.advanceUntilIdle()`
     * which progress time until all suspending functions complete or throws exception
     */
    @Test
    fun `First Coroutine test`() = runBlockingTest {

        println("Test started")

        // GIVEN
        val expected = "Hello World"

        // WHEN
        val actual = async {
            mockSomeDelayedWork()
        }

        // THEN
        Truth.assertThat(actual.await()).isEqualTo(expected)
        println("Test finished")

    }

    /**
     *
     * ❌ This test FAILS because it's not [TestCoroutineDispatcher]
     * that calling [TestCoroutineDispatcher.runBlockingTest] or [withContext] is not
     * invoked with [CoroutineContext] of [runBlockingTest].
     *
     */
    @Test
    fun `Test coroutine with runBlockingTest that fails`() = runBlockingTest {

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

        advanceUntilIdle()

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
    fun test() = runBlockingTest {

        val subject = Subject()

        var actual = 0

        launch {
            actual = subject.getIntAfterDelay(3000)
        }

        advanceTimeBy(3000)
        Truth.assertThat(actual).isEqualTo(12)

    }

    /*
        *** Time Out ***
     */
    /**
     * ❌ THIS TEST FAILS even with advanceTimeBy or advanceUntilIdle
     */
    @Test(expected = TimeoutCancellationException::class)
    fun `Test with timeout with launch`() = runBlockingTest {

        launch {
            mockResponseWitTimeout(2000, 3000)
        }
    }


    /**
     * ✅ THIS TEST PASSES if timeout is shorter than delay time
     */
    @Test(expected = TimeoutCancellationException::class)
    fun `Test with timeout with async`() = runBlockingTest {

        val test = async {
            mockResponseWitTimeout(2000, 3000)
        }

        Truth.assertThat(test.await()).isSameInstanceAs(TimeoutCancellationException::class)
    }

    @Test(expected = RuntimeException::class)
    fun `Test exception`() = runBlockingTest {

        delay(2000)
        throw RuntimeException()
    }

    @Test(expected = RuntimeException::class)
    fun `Test exception with launch`() = runBlockingTest {

        launch {
            delay(2000)
            throw RuntimeException()
        }
    }


    /*
        ******** TESTS with TestCoroutineDispatcher  ********
     */

    @Test
    fun `Test using TestCoroutineDispatcher with async`() =
        testCoroutineDispatcher.runBlockingTest {

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


    /*
        Throw Exception
     */

    @Test(expected = RuntimeException::class)
    fun `Throw exception using TestCoroutineDispatcher`() =
        testCoroutineDispatcher.runBlockingTest {
            throwExceptionAfterDelay()
        }

    @Test(expected = RuntimeException::class)
    fun `Throw exception using TestCoroutineDispatcher with async`() =
        testCoroutineDispatcher.runBlockingTest {
            async {
                throwExceptionAfterDelay()
            }.await()
        }

    @Test(expected = RuntimeException::class)
    fun `Throw exception using TestCoroutineDispatcher with launch`() =
        testCoroutineDispatcher.runBlockingTest {
            launch {
                throwExceptionAfterDelay()
            }
        }


    /*
        Time Out
     */

    @Test(expected = TimeoutCancellationException::class)
    fun `Test timeout using TestCoroutineDispatcher`() =
        testCoroutineDispatcher.runBlockingTest {
            mockResponseWitTimeout(2000, 3000)
        }

    /**
     *  ❌ This test FAILS ???
     */
    @Test(expected = TimeoutCancellationException::class)
    fun `Test timeout using TestCoroutineDispatcher with launch`() =
        testCoroutineDispatcher.runBlockingTest {

            launch(testCoroutineDispatcher) {
                mockResponseWitTimeout(2000, 3000)
            }

        }


    @Test(expected = TimeoutCancellationException::class)
    fun `Test timeout with TestCoroutineDispatcher with async`() =
        testCoroutineDispatcher.runBlockingTest {

            val deferred = async(testCoroutineDispatcher) {
                mockResponseWitTimeout(2000, 3000)
            }

            Truth.assertThat(deferred.await()).isSameInstanceAs(TimeoutCancellationException::class)
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

    private suspend fun mockSomeDelayedWork(timeInMillis: Long = 3000): String {
        delay(timeInMillis)
        return "Hello World"
    }

    private suspend fun mockResponseWitTimeout(
        timeOut: Long = 2000,
        responseDelayTime: Long = 3000
    ): String {

        return withTimeout(timeMillis = timeOut) {
            mockSomeDelayedWork(responseDelayTime)
        }
    }


    private suspend fun throwExceptionAfterDelay() {
        println("⛱ fooWithException() BEFORE delay in thread: ${Thread.currentThread()}")
        delay(6_000)
        println("😎 fooWithException() AFTER delay in thread: ${Thread.currentThread()}")
        throw RuntimeException("Failed via TEST exception")

    }


}

class Subject() {

    var result = 0

    suspend fun setResultAfterDelay(timeInMillis: Long) {
        result = -1
        delay(timeInMillis)
        result = 15
    }

    suspend fun getIntAfterDelay(timeInMillis: Long = 3000): Int {
        delay(timeInMillis)
        return 12
    }

    suspend fun getIntOrThrowExceptionAfterTimeout(
        timeOut: Long = 2000,
        responseDelayTime: Long = 3000
    ): Int {

        return withTimeout(timeMillis = timeOut) {
            getIntAfterDelay(responseDelayTime)
        }
    }


    suspend fun throwExceptionAfterDelay() {
        println("⛱ fooWithException() BEFORE delay in thread: ${Thread.currentThread()}")
        delay(6_000)
        println("😎 fooWithException() AFTER delay in thread: ${Thread.currentThread()}")
        throw RuntimeException("Failed via TEST exception")

    }

}
