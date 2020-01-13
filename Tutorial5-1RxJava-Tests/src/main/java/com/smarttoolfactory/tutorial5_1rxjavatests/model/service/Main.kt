package com.smarttoolfactory.tutorial5_1rxjavatests.model.service

/**
 * Created by Nathan on 11/09/15.
 */
class Main {

    fun main() {
        val viewModel = ViewModel(ApiService())


        val disposable = viewModel.getCountriesObservable().subscribe {
            println("Result $it")
        }

//        disposable.dispose()
    }
}