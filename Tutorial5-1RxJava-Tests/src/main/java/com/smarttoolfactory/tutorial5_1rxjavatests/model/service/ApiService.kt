package com.smarttoolfactory.tutorial5_1rxjavatests.model.service

import io.reactivex.Observable
import java.util.*

class ApiService {

    fun getCountries(): Observable<List<String>> {

        val countries: MutableList<String> =
            ArrayList()
        countries.add("USA")
        countries.add("China")
        countries.add("Atlantis")
        return Observable.just(countries)

    }
}