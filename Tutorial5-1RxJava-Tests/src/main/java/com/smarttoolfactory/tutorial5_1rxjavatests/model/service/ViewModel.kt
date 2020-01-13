package com.smarttoolfactory.tutorial5_1rxjavatests.model.service

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class ViewModel(private val apiService: ApiService) {

    var isRequesting = false
        private set

    fun getCountriesObservable(): Observable<String> {

        return apiService.getCountries()

            .doOnSubscribe {
                isRequesting = true
            }

            .doOnTerminate { isRequesting = false }
            .flatMapIterable { countries: List<String>? -> countries }
            .filter { country: String ->
                !country.equals(
                    "Atlantis",
                    ignoreCase = true
                )
            }

    }

}