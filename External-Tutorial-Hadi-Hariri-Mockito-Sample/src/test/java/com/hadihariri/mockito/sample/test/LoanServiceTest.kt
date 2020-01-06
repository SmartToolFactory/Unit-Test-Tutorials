package com.hadihariri.mockito.sample.test

import com.hadihariri.mockito.sample.LoanCalculator
import com.hadihariri.mockito.sample.LoanService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class LoanServiceTest {

    @InjectMocks
    private lateinit var loanService: LoanService

    @Mock
    private lateinit var mockLoanCalculator: LoanCalculator


    @Test
    fun authoriseCustomerLoan() {

        // Given
        `when`(mockLoanCalculator.calculateAmount(3)).thenReturn(300.0)

        // When
        val amount = loanService.authoriseCustomerLoan(3)

        // Then
        assertEquals(300.0, amount)

    }
}