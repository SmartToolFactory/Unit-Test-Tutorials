package com.smarttoolfactory.tutorial4_2kotlin_mockk.viewmodel

import kotlinx.coroutines.delay

class CalculationUseCase {


    suspend fun doCalculationWithDelay(base: Int): Int {

        delay(2000)
        return base * 10
    }
}