package com.smarttoolfactory.tutorial5_1rxjavatests

import com.smarttoolfactory.tutorial5_1rxjavatests.model.concurrency.DelayedRepository
import com.smarttoolfactory.tutorial5_1rxjavatests.model.concurrency.Image
import com.smarttoolfactory.tutorial5_1rxjavatests.model.concurrency.ImageDownloader
import io.reactivex.schedulers.TestScheduler
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis


class ConcurrencyTest {

    lateinit var imageDownloader: ImageDownloader

    @Test
    fun `Total test time takes 5 seconds with delay`() {
        imageDownloader = ImageDownloader(DelayedRepository())

        val totalTime = measureTimeMillis {
            val actualList = imageDownloader.downloadImages().blockingGet()

            assertTrue(
                actualList.containsAll(
                    setOf(
                        Image(1),
                        Image(2),
                        Image(3),
                        Image(4),
                        Image(5)
                    )
                )
            )
        }
        println("Total time: $totalTime")
        assertTrue(totalTime < 6000)
    }

    /**
     * This test calls flatMap operator which invokes emission in parallel
     */
    @Test
    fun `Concurrent test with flatMap operator`() {

        // Given
        val testScheduler = TestScheduler()
        imageDownloader = ImageDownloader(DelayedRepository(testScheduler), testScheduler)

        val actualList = mutableListOf<Image>()

        // When
        val disposable = imageDownloader.downloadImages()
            .subscribe { list -> actualList.addAll(list) }

        // Then
        // After 4 seconds, we can assert that our list is still empty.
        testScheduler.advanceTimeBy(4, TimeUnit.SECONDS)
        assertTrue(actualList.isEmpty())

        // After a 5th second, we can assert that our list is loaded.
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        assertTrue(
            actualList.containsAll(
                setOf(
                    Image(1),
                    Image(2),
                    Image(3),
                    Image(4),
                    Image(5)
                )
            )
        )

        disposable.dispose()

    }

    /**
     * This test calls conta
     */
    @Test
    fun `Sequential test with concatMap operator`() {

        // Given
        val testScheduler = TestScheduler()
        imageDownloader = ImageDownloader(DelayedRepository(testScheduler), testScheduler)

        val actualList = mutableListOf<Image>()

        // When
        val disposable = imageDownloader.downloadImagesSequentially()
            .subscribe { list -> actualList.addAll(list) }

        // Then
        // 24 seconds should pass without any success.
        testScheduler.advanceTimeBy(24, TimeUnit.SECONDS)
        assertTrue(actualList.isEmpty())

        // The stream will emit on the 25th second.
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        assertTrue(
            actualList.containsAll(
                setOf(
                    Image(1),
                    Image(2),
                    Image(3),
                    Image(4),
                    Image(5)
                )
            )
        )

        disposable.dispose()
    }
}