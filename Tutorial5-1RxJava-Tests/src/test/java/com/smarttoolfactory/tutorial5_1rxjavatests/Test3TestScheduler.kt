package com.smarttoolfactory.tutorial5_1rxjavatests

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class Test3TestScheduler {


    @Test
    fun usingTestScheduler() {

        //Declare TestScheduler
        val testScheduler = TestScheduler()

        //Declare TestObserver
        val testObserver: TestObserver<Long> = TestObserver()

        //Declare Observable emitting every 1 minute
        val minuteTicker = Observable.interval(
            1, TimeUnit.MINUTES,
            testScheduler
        )

        //Subscribe to TestObserver
        minuteTicker.subscribe(testObserver)

        //⏱ Fast forward by 30 seconds
        testScheduler.advanceTimeBy(30, TimeUnit.SECONDS)

        // Get current virtual time of test scheduler in seconds
        val currentVirtualTime = testScheduler.now(TimeUnit.SECONDS)
        println("Current virtual time: $currentVirtualTime")

        //Assert no emissions have occurred yet
        testObserver.assertValueCount(0)

        //⏱ Fast forward to 70 seconds after subscription
        testScheduler.advanceTimeTo(70, TimeUnit.SECONDS)

        //Assert the first emission has occurred
        testObserver.assertValueCount(1)

        //⏱ Fast Forward to 90 minutes after subscription
        testScheduler.advanceTimeTo(90, TimeUnit.MINUTES)

        //Assert 90 emissions have occurred
        testObserver.assertValueCount(90)

        testObserver.dispose()
    }


    /**
     * 🔥 INFO advanceTimeTo
     *
     * As the name suggests, advanceTimeTo will execute all actions that
     * are scheduled for up to a specific moment in time.
     *
     * That includes actions scheduled while the scheduler was being fast-forwarded,
     * i.e. actions scheduled by other actions.
     */

    @Test
    fun advanceTimeToOperator() {

        val testScheduler = TestScheduler()

        testScheduler.createWorker().schedule { println("Immediate") }

        testScheduler.createWorker().schedule(
            { println("20s") },
            20, TimeUnit.SECONDS
        )

        testScheduler.createWorker().schedule(
            { println("40s") },
            40, TimeUnit.SECONDS
        )

        println("Advancing to 1ms")
        testScheduler.advanceTimeTo(1, TimeUnit.MILLISECONDS)
        println("Virtual time: " + testScheduler.now(TimeUnit.MILLISECONDS))

        println("Advancing to 10s")
        testScheduler.advanceTimeTo(10, TimeUnit.SECONDS)
        println("Virtual time: " + testScheduler.now(TimeUnit.MILLISECONDS))

        println("Advancing to 40s")
        testScheduler.advanceTimeTo(40, TimeUnit.SECONDS)
        println("Virtual time: " + testScheduler.now(TimeUnit.MILLISECONDS))

        /*
            Prints:
            Advancing by 1ms
            Immediate
            Virtual time: 1
            Advancing by 10s
            Virtual time: 10001
            Advancing by 40s
            20s
            40s
            Virtual time: 50001
         */

        /**
         * We scheduled 3 tasks: one to be executed immediately, and two to be executed in the future.
         * Nothing happens until we advance time, including the tasks scheduled immediately.
         * When we advance time, the scheduler synchronously executes all the tasks
         * that were scheduled for that period of time, in the order of the time they were scheduled for.
         *
         * advanceTimeTo allows you to set time to any value, including one
         * that is before the current time. This implementation decision
         * can needlessly introduce bugs in the tests, so it is probably
         * better to use the next method, when applicable.
         */


    }


    @Test
    fun `Test list with advanceTimeBy method`() {

        val testScheduler = TestScheduler()
        val expected: List<Long> = Arrays.asList(0L, 1L, 2L, 3L, 4L)
        val result: MutableList<Long> = ArrayList()

        Observable
            .interval(1, TimeUnit.SECONDS, testScheduler)
            .take(5)
            .subscribe { i: Long ->
                result.add(i)
            }

        assertTrue(result.isEmpty())
        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS)
        assertTrue(result == expected)

    }

    /**
     * triggerActions does not advance time.
     * It only executes actions that were scheduled to be executed up to the present.
     */
    @Test
    fun `Test triggerAction does not advance time`() {

        val testScheduler = TestScheduler()

        testScheduler.createWorker().schedule { println("Immediate") }

        testScheduler.createWorker().schedule(
            { println("20s") },
            20, TimeUnit.SECONDS
        )

        val actual = testScheduler.now(TimeUnit.SECONDS)
        testScheduler.triggerActions()
        println("Virtual time: $actual")

        assertEquals(actual, 0)

        /*
            Prints:
            Immediate
            Virtual time: 0
         */
    }



    @Test
    fun `Test list with triggerActions method`() {

        val testScheduler = TestScheduler()
        val expected: List<Long> = listOf(0L, 1L, 2L, 3L, 4L)
        val result: MutableList<Long> = ArrayList()

        Observable
            .interval(1, TimeUnit.SECONDS, testScheduler)
            .take(5)
            .subscribe { i: Long ->
                result.add(i)
            }

        assertTrue(result.isEmpty())

        // 🔥 Trigger action does not advance time
        testScheduler.triggerActions()
        assertNotEquals(result, expected)

        val actual = testScheduler.now(TimeUnit.SECONDS)
        println("Virtual time: $actual")

        /*
            Prints:
            Virtual time: 0
         */

    }


    @Test
    fun `Test error after a delay with advanceTimeBy method`() {

        val testScheduler = TestScheduler()

        val observer = Single.error<Int>(RuntimeException())
            .delay(5, TimeUnit.SECONDS, testScheduler)
            .test()

        println("Real time: ${testScheduler.now(TimeUnit.SECONDS)}")

        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS)

        println("Virtual time: ${testScheduler.now(TimeUnit.SECONDS)}")

        observer.assertError(RuntimeException::class.java)

        observer.dispose()

        /*
            Prints:
            Real time: 0
            Virtual time: 5
         */
    }

    @Test
    fun `Test error with triggerActions method`() {

        val testScheduler = TestScheduler()

        val observer = Single.error<Int>(RuntimeException())
            .delay(5, TimeUnit.SECONDS, testScheduler)
            .test()

        println("Real time: ${testScheduler.now(TimeUnit.SECONDS)}")

        // 🔥 Fails if not called
        testScheduler.triggerActions()

        println("Virtual time: ${testScheduler.now(TimeUnit.SECONDS)}")

        observer.assertError(RuntimeException::class.java)

        observer.dispose()

        /*
            Prints:
            Real time: 0
            Virtual time: 0
         */
    }


    @Test
    fun `Test timeout with triggerActions method`() {

        val testScheduler = TestScheduler()

        val testObserver: TestObserver<String> = Single.just<String>("EXPECTED")
            .delay(5, TimeUnit.SECONDS, testScheduler)
            .doOnError {
                println("doOnError() ${it.message}")
            }
            .doOnSuccess {
                println("doOnSuccess() $it")
            }
            .test()


        testScheduler.triggerActions()

        testObserver.assertValue("EXPECTED")

    }


    @Test
    fun `Test with triggerActions`() {


        val actual = Single.just<String>("EXPECTED")
            .delay(5, TimeUnit.SECONDS)
            .doOnError {
                println("doOnError() ${it.message}")
            }
            .doOnSuccess {
                println("doOnSuccess() $it")
            }
            .blockingGet()


        assertEquals(actual, "EXPECTED")

    }
}