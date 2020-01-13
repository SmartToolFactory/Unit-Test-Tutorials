package com.smarttoolfactory.tutorial5_1rxjavatests.model.concurrency

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

data class Image(val imageId: Int)

interface ImageRepository {
    fun downloadImageById(imageId: Int): Single<Image>
}

class ImageDownloader(private val imageRepository: ImageRepository,
                      private val ioScheduler: Scheduler = Schedulers.io()) {

    fun downloadImages(): Single<List<Image>> = Observable.fromArray(1, 2, 3, 4, 5)
            .flatMap {
                imageRepository.downloadImageById(it)
                        .subscribeOn(ioScheduler)
                        .toObservable()
            }
            .toList()

    fun downloadImagesSequentially(): Single<List<Image>> = Observable.fromArray(1, 2, 3, 4, 5)
            .concatMap {
                imageRepository.downloadImageById(it)
                        .subscribeOn(ioScheduler)
                        .toObservable()
            }
            .toList()

}

class DelayedRepository(private val delayScheduler: Scheduler = Schedulers.computation()) :
    ImageRepository {

    override fun downloadImageById(imageId: Int): Single<Image> {
        return Single.just(Image(imageId))
                .delay(5, TimeUnit.SECONDS, delayScheduler)
    }

}