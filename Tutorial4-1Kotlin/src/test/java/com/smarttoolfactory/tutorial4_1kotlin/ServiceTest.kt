package com.smarttoolfactory.tutorial4_1kotlin

import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class ServiceTest {

    open class Generator {
        open fun generate(): String = "Random String that's not random"
    }

    open class Dao {
        open fun insert(record: String) = println("""Inserting "$record"""")
    }

    open class Service(private val generator: Generator, private val dao: Dao) {

        open fun calculate() {
            val record = generator.generate()
            dao.insert(record)
        }

    }

    private val generator: Generator = mock(Generator::class.java)
    private val dao: Dao = mock(Dao::class.java)

    private val service = Service(generator, dao)

    @Test
    fun myTest() {
        val mockedRecord = "mocked String"
        `when`(generator.generate()).thenReturn(mockedRecord)

        service.calculate()

        verify(generator).generate()
        verify(dao).insert(mockedRecord)
        verifyNoMoreInteractions(generator, dao)
    }
}