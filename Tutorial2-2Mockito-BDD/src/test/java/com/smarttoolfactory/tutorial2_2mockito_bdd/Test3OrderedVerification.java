package com.smarttoolfactory.tutorial2_2mockito_bdd;

import com.smarttoolfactory.tutorial2_2mockito_bdd.model_math_application.CalculatorService;
import com.smarttoolfactory.tutorial2_2mockito_bdd.model_math_application.MathApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class Test3OrderedVerification {

    private MathApplication mathApplication;
    private CalculatorService calcService;

    @Before
    public void setUp() {
        mathApplication = new MathApplication();
        calcService = mock(CalculatorService.class);
        mathApplication.setCalculatorService(calcService);
    }

    @Test
    public void testAddAndSubtract() {

        // Given
        given(calcService.add(20.0, 10.0)).willReturn(30.0);
        given(calcService.subtract(20.0, 10.0)).willReturn(10.0);

        //create an inOrder verifier for a single mock
        InOrder inOrder = inOrder(calcService);

        // When
        double expectedAdd = mathApplication.add(20.0, 10.0);
        double expectedSubtract = mathApplication.subtract(20.0, 10.0);

        // Then
        then(calcService).should(inOrder).add(20.0, 10.0);
        then(calcService).should(inOrder).subtract(20.0, 10.0);

        assertThat(expectedAdd, closeTo(30.0, 0.0));
        assertThat(expectedSubtract, closeTo(10.0, 0.0));


    }
}