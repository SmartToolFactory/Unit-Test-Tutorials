package com.smarttoolfactory.tutorial4_2kotlin_mockk

import com.smarttoolfactory.tutorial4_2kotlin_mockk.car.Car
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

class TrafficSystem {

    lateinit var car1: Car

    lateinit var car2: Car

    lateinit var car3: Car

}

class CarTest {

    @MockK
    lateinit var car1: Car

    @RelaxedMockK
    lateinit var car2: Car

    @MockK(relaxUnitFun = true)
    lateinit var car3: Car

    @SpyK
    var car4 = Car()

    @InjectMockKs
    var trafficSystem = TrafficSystem()

    // turn relaxUnitFun on for all mocks
    @BeforeEach
    fun setUp() =
        MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun calculateAddsValues1() {
        // ... use car1, car2, car3 and car4
    }
}

@ExtendWith(MockKExtension::class)
class CarTestJunit5 {
    @MockK
    lateinit var car1: Car

    @RelaxedMockK
    lateinit var car2: Car

    @MockK(relaxUnitFun = true)
    lateinit var car3: Car

    @SpyK
    var car4 = Car()

    @Test
    fun calculateAddsValues1() {
        // ... use car1, car2, car3 and car4
    }

}

class CarTestWithFunctionParam{

    @Test
    fun calculateAddsValues1(@MockK car1: Car, @RelaxedMockK car2: Car) {
        // ... use car1 and car2
    }
}