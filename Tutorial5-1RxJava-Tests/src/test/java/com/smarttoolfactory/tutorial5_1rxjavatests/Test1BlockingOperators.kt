package com.smarttoolfactory.tutorial5_1rxjavatests

import io.reactivex.Observable
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


/**
 * When it comes to unit testing, the unit test usually has to complete
 * before it starts the next one.
 * This can become quite messy when we have an Observable or Flowable
 * operation that happens on a different thread.
 * When a test method declares an asynchronous Observable or Flowable chain operation,
 * we need to block and wait for that operation to complete.
 */
class Test1BlockingOperators {


    /**
     * 🔥 INFO blockingSubscribe
     *
     * When you run this test, the assertion fails.
     *
     * Observable.interval() is running on a computation thread
     * and the main thread rushes past it.
     *
     * The main thread performs assertTrue() before the five emissions are fired
     * and therefore finds hitCount to be 0 rather than 5.
     * We need to stop the main thread until subscribe() finishes and calls onComplete().
     */
    @Test
    fun `Test subscribe`() {

        // Given

        // When
        val hitCount = AtomicInteger()

        val source = Observable
            .interval(1, TimeUnit.SECONDS)
            .take(5)
            .map {
                println("Thread is ${Thread.currentThread().name}")
                it
            }

        source.subscribe {
            hitCount.incrementAndGet()
        }

        println("End of when")


        // Then
        //👎 Fails
        assertTrue(hitCount.get() == 5)

    }

    @Test
    fun `Test blockingSubscribe`() {

        // Given

        // When
        val hitCount = AtomicInteger()

        val source = Observable
            .interval(1, TimeUnit.SECONDS)
            .map {
                println("Thread is ${Thread.currentThread().name}")
                it
            }
            .take(5)

        source
            .blockingSubscribe {
                hitCount.incrementAndGet()
            }

        println("End of when")

        // Then
        // 👍 Passes
        assertTrue(hitCount.get() == 5)

        /*
            Prints:
            Thread is RxComputationThreadPool-1
            Thread is RxComputationThreadPool-1
            Thread is RxComputationThreadPool-1
            Thread is RxComputationThreadPool-1
            Thread is RxComputationThreadPool-1
            End of when

         */

    }

    /**
     * 🔥 INFO blockingFirst
     *
     * The blockingFirst() operator will stop the calling thread and make it wait
     * for the first value to be emitted and returned (even if the chain is operating
     * on a different thread with observeOn() and subscribeOn()).
     *
     * Say we want to test an Observable chain that filters a sequence of string emissions for only
     * ones that have a length of four.
     *
     * If we want to assert that the first emission to make it through this operation is Beta
     */
    @Test
    fun `Test blockingFirst`() {

        // Returns the first element from emission
        val firstWithLengthFour = getTestObservable()
            .filter { it.length == 4 }.blockingFirst()

        // 👍 Passes
        assertTrue(firstWithLengthFour == "Beta")

        /*
             Here, our unit test is called testFirst(),
             and it will assert that the first string emitted with a length of four is Beta.

              Note that instead of using subscribe() or blockingSubscribe() to receive the emissions,
              we use blockingFirst(), which will return the first emission in a non-reactive way.

              In other words, it returns a straight-up string and not an Observable emitting string.
         */

    }

    /**
     * 🔥 INFO blockingGet
     *
     * Maybe and Single do not have blockingFirst() since there can only be one element at most.
     * Logically, for a Single and Maybe, it is not exactly the first element, but rather the only element,
     * so the equivalent operator is blockingGet().
     *
     * Here, we assert that all items of length four include only Beta and Zeta,
     * and we collect them with toList(), which yields a Single<List<String>>.
     *
     * We can use blockingGet() to wait for this list and assert that it is equal to our desired result:
     */
    @Test
    fun `Test blockingGet`() {

        // Given

        // When
        val single = getTestObservable()
            .filter { it.length == 4 }
            .toList()

        val allWithLengthFour: List<String> = single.blockingGet()

        // Then
        // 👍 Passes
        assertIterableEquals(allWithLengthFour, listOf("Beta", "Zeta"))
    }

    /**
     * 🔥 INFO  blockingLast()
     *
     * If there is blockingFirst(), it only makes sense to have blockingLast().
     * This will block and return the last value to be emitted from an Observable or Flowable operation.
     *
     * Of course, it will not return anything until onComplete() is called,
     * so this is something you will want to avoid using with <strong>infinite</strong> sources.
     */
    @Test
    fun `Test blockingLast`() {

        //Given

        // When
        val firstWithLengthFour = getTestObservable()
            .filter { it.length == 4 }
            .doOnComplete {
                println("doOnComplete()")
            }
            .blockingLast()

        println("End of when")

        // Then
        // 👍 Passes
        assertTrue(firstWithLengthFour == "Zeta")
    }

    /**
     * 🔥 INFO blockingIterable()
     *
     * One of the most interesting blocking operators is blockingIterable().
     * Rather than returning a single emission like our previous examples,
     * it will provide the emissions as they become available through iterable<T>.
     * The Iterator<T> provided by the Iterable<T> will keep blocking the iterating thread
     * until the next emission is available, and the iteration will end when onComplete() is called.
     *
     * Here, we iterate through each returned string value to ensure that its length is actually 5:
     */
    @Test
    fun `Test blockingIterable`() {

        // When
        val allWithLengthFive: Iterable<String> =
            getTestObservable()
                .filter { it.length == 5 }
                .blockingIterable()

        allWithLengthFive.forEach {
            assertTrue(it.length == 5)
        }

    }

    /**
     * 🔥INFO blockingForEach()
     *
     * A more fluent way in which we can execute a blocking for each task is to use the
     * blockingForEach() operator instead of blockingIterable().
     *
     * This will block the declaring thread and wait for each emission to be processed
     * before allowing the thread to continue.
     */
    @Test
    fun `Test blockingForEach`() {

        getTestObservable()
            .filter { it.length == 5 }
            .blockingForEach {
                assertTrue(it.length == 5)
            }

        /*
            A variant of blockingForEach() is blockingForEachWhile(),
            which accepts a predicate that gracefully terminates the sequence
            if the predicate evaluates to false against an emission.

            This can be desirable if all emissions are not going to be consumed
            and you want to gracefully terminate.
         */
    }

    /**
     * 🔥 INFO blockingNext()
     * <p>
     * The blockingNext() will return an iterable and block each iterator's next() request
     * until the next value is provided. Emissions that occur after the last fulfilled next() request
     * and before the current next() are ignored.
     *
     * Here, we have a source that emits every microsecond (1/1000th of a millisecond).
     *
     * Note that the iterable returned from blockingNext() ignored previous values it missed:
     */
    @Test
    fun `Test blockingNext`() {

        val source =
            Observable.interval(1, TimeUnit.MICROSECONDS)
                .take(1000)

        val iterable: Iterable<Long> = source.blockingNext()

        iterable.forEach {
            println("Value: $it")
        }

        /*
            Prints:
            Value: 0
            Value: 6
            Value: 10
            Value: 14
            Value: 17
            Value: 44
            Value: 47
            Value: 59
         */

    }

    @Test
    fun `Test blockingLatest`() {

        val source =
            Observable.interval(1, TimeUnit.MICROSECONDS)
                .take(1000)

        val iterable: Iterable<Long> = source.blockingLatest()

        iterable.forEach {
            println("Value: $it")
        }


    }

    /**
     * 🔥 INFO blockingMostRecent()
     * The blockingMostRecent() is similar to blockingLatest(),
     * but it will re-consume the latest value repeatedly for every next() call from the iterator
     * even if it was consumed already.
     *
     * It also requires a defaultValue argument so it has something to return if no value is emitted yet.
     *
     * Here, we use blockingMostRecent() against an Observable emitting every 10 milliseconds.
     * The default value is -1, and it consumes each value repeatedly until the next value is provided:
     *
     * Returns last value in specified interval until next one comes
     */
    @Test
    fun testBlockingMostRecent() {
        val source =
            Observable.interval(10, TimeUnit.MILLISECONDS)
                .take(5)
        val iterable = source.blockingMostRecent(-1L)
        for (i in iterable) {
            println(i)
        }

        /*
            Prints:
            -1
            -1
            -1
            ...
            0
            0
            0
            ...
            1
            1
            1
            ...
         */
    }

    private fun getTestObservable(): Observable<String> {
        return Observable.just("Alpha", "Beta", "Gamma", "Delta", "Zeta")
    }


}



