package com.smarttoolfactory.tutorial5_1rxjavatests

import com.smarttoolfactory.tutorial5_1rxjavatests.model.service.ApiService
import com.smarttoolfactory.tutorial5_1rxjavatests.model.service.ViewModel
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations
import org.mockito.MockitoAnnotations.initMocks
import java.util.*

internal class ViewModelTest {

    @Mock
    private lateinit var mockApiService: ApiService

    @BeforeEach
    fun setUp() {
        initMocks(this)
    }

    @Test
    fun test_CountriesObservable_FiltersOutAtlantis() {

        // Given
        val inputCountries: MutableList<String> = ArrayList()
        inputCountries.add("USA")
        inputCountries.add("China")
        inputCountries.add("Atlantis")

        val mockObservable = Observable.just<List<String>>(inputCountries)

        doReturn(mockObservable).`when`(mockApiService).getCountries()

        val expectedCountries: MutableList<String> = ArrayList()
        expectedCountries.add("USA")
        expectedCountries.add("China")

        val viewModel = ViewModel(mockApiService)
        val testObserver = TestObserver<String>()

        // When
        viewModel.getCountriesObservable()
            .doOnNext {
                println("doOnNext countries: $it")
            }
            .subscribe(testObserver)

        // Then
        testObserver.assertValueSequence(expectedCountries)
    }

    @Test
    fun test_CountriesObservable_StartsRequestWhenSubscribedTo() {

        // Given
        //Never will never complete so you can use it to keep prevent onComplete or onError being called
        val mockObservable = Observable.never<List<String>>()

        doReturn(mockObservable).`when`(mockApiService).getCountries()

        val viewModel = ViewModel(mockApiService)
        val testObserver = TestObserver<String>()

        // When
        viewModel.getCountriesObservable().subscribe(testObserver)

        // Then
        assertTrue(viewModel.isRequesting)
    }

    @Test
    fun test_CountriesObservable_FinishesRequestWhenCompleted() {

        // Given
        //Empty will onComplete right away with no events emitted
        val mockObservable = Observable.empty<List<String>>()

        doReturn(mockObservable).`when`(mockApiService).getCountries()

        val viewModel = ViewModel(mockApiService)

        val testObserver = TestObserver<String>()

        // When
        viewModel.getCountriesObservable().subscribe(testObserver)

        // Then
        assertFalse(viewModel.isRequesting)
    }
}