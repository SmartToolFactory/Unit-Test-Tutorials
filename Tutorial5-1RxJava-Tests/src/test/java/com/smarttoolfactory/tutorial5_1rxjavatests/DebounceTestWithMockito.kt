package com.smarttoolfactory.tutorial5_1rxjavatests

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.util.concurrent.TimeUnit

@ExtendWith(MockitoExtension::class)
class DebounceTestWithMockito {
    private lateinit var searchSubject: PublishSubject<String>

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var presenter: Presenter

    private lateinit var disposable: Disposable

    private lateinit var ioTestScheduler: TestScheduler
    private lateinit var uiTestScheduler: TestScheduler

    @BeforeEach
    fun setUp() {
        searchSubject = PublishSubject.create<String>()

        ioTestScheduler = TestScheduler()
        uiTestScheduler = TestScheduler()

        setupSearch(uiTestScheduler, ioTestScheduler)

        // important https://stackoverflow.com/a/53543257/1355048
        ioTestScheduler.triggerActions()
    }

    @AfterEach
    fun tearDown() {
        disposable.dispose()
    }

    @Test
    fun searchBillsTest() {
        `when`(apiService.search("America")).thenReturn(Observable.just("MOCK RESULT"))

        searchSubject.onNext("America")

        ioTestScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        // Triggers actions on UI thread too
        uiTestScheduler.triggerActions()

        verify(presenter).doSomething("MOCK RESULT")
    }

    private fun setupSearch(uiScheduler: Scheduler, ioScheduler: Scheduler) {

        disposable = searchSubject.debounce(1, TimeUnit.SECONDS, ioScheduler)
            .distinctUntilChanged()
            .switchMap { apiService.search(it) }
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe { response ->
                presenter.doSomething(response)
            }

    }

    interface ApiService {
        fun search(query: String): Observable<String>
    }

    interface Presenter {
        fun doSomething(result: String)
    }
}