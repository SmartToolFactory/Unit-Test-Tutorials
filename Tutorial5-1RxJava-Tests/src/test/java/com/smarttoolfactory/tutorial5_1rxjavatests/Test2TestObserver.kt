package com.smarttoolfactory.tutorial5_1rxjavatests

import com.smarttoolfactory.tutorial5_1rxjavatests.model.User
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

class Test2TestObserver {

    @Test
    fun `Test methods of TestObserver`() {

        //An Observable with 5 one-second emissions
        val source = Observable.interval(
            1,
            TimeUnit.SECONDS
        )
            .take(5)

        //Declare TestObserver
        val testObserver: TestObserver<Long> = TestObserver()

        // Alternative 2 to declare test observer
//        val testObserver = source.test()

        //Assert no subscription has occurred yet
        testObserver.assertNotSubscribed()

        //Subscribe TestObserver to source
        source.subscribe(testObserver)

        // Subscribes here
        //Assert TestObserver is subscribed
        testObserver.assertSubscribed()

        //🔥🔥 Block and wait for Observable to terminate
        testObserver.awaitTerminalEvent()

        //Assert TestObserver called onComplete()
        testObserver.assertComplete()

        //Assert there were no errors
        testObserver.assertNoErrors()

        //Assert 5 values were received
        testObserver.assertValueCount(5)

        //Assert the received emissions were 0, 1, 2, 3, 4
        testObserver.assertValues(0L, 1L, 2L, 3L, 4L)

        testObserver.dispose()

    }

    @Test
    fun `Test with zip operator`() {

        val letters = listOf("A", "B", "C", "D", "E")
        val testObserver = TestObserver<String>()

        val observable = Observable.fromIterable(letters)
            .zipWith(
                Observable.range(1, Integer.MAX_VALUE),
                BiFunction { string: String, index: Int ->
                    "$index-$string"
                })


        observable.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(5)

        testObserver.assertValues("1-A", "2-B", "3-C", "4-D", "5-E")

        testObserver.dispose()

    }


    /**
     * Assert that Observable throws exception
     */
    @Test
    fun `Test that observable throws Runtime Exception`() {

        val letters = listOf("A", "B", "C", "D", "E")
        val testObserver = TestObserver<String>()

        val observable = Observable.fromIterable(letters)
            .zipWith(
                Observable.range(1, Integer.MAX_VALUE),
                BiFunction { string: String, index: Int ->
                    "$index-$string"
                })
            .concatWith(Observable.error(RuntimeException("error in Observable")))

        observable.subscribe(testObserver)

        // Assert that throws RuntimeException
        testObserver.assertError(RuntimeException::class.java)

        // Assert that onComplete not called
        testObserver.assertNotComplete()

        testObserver.dispose()

    }

    @Test
    fun `Test got an user`() {
        val result = getUserObservableWithDelay("John", "Wick")

        val testObserver = TestObserver<User>()

        result
            .subscribeOn(Schedulers.io())
            .doOnNext { println("doOnNext() $it") }
            .doOnComplete { println("doOnComplete() ${Thread.currentThread().name}") }
            .subscribe(testObserver)


        // Waits answer to mock answer to come after delay
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        testObserver.assertValues(User("John", "Wick"))

    }


    /**
     * Returns a User object after 3 seconds of delay.
     *
     * First map operator is executed instantly, then waits 3 seconds and fires second map
     * and returns user
     */
    private fun getUserObservableWithDelay(name: String, surName: String): Observable<User> {


        return Observable.just(User(name, surName))

            .map {
                println("START thread: ${Thread.currentThread().name}")
                it
            }
            .delay(3, TimeUnit.SECONDS)
            .map {
                println("getUserObservable() -> map() $it, thread: ${Thread.currentThread().name}")
                it
            }
            .subscribeOn(Schedulers.io())

    }

}