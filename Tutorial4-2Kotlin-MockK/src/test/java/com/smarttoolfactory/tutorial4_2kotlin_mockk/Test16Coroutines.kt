package com.smarttoolfactory.tutorial4_2kotlin_mockk

import com.google.common.truth.Truth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class Test16Coroutines {

    @Test
    fun `First Coroutine test`()= runBlocking {

        // GIVEN
        val expected = "Hello World"

        // WHEN
        val actual = GlobalScope.async {
            mockSomeDelayedWork()
        }

        // THEN
        Truth.assertThat(actual.await()).isEqualTo(expected)
    }


}


private suspend fun mockSomeDelayedWork(): String {

    delay(10_000)
    return "Hello World"

}

