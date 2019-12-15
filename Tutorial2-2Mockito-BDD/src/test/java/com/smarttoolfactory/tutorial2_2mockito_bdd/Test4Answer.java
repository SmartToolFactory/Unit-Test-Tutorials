package com.smarttoolfactory.tutorial2_2mockito_bdd;

import com.smarttoolfactory.tutorial2_2mockito_bdd.model_math_application.CalculatorService;
import com.smarttoolfactory.tutorial2_2mockito_bdd.model_math_application.MathApplication;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Test4Answer {

    @InjectMocks
    MathApplication mathApplication;

    @Mock
    CalculatorService calcService;


    @Test
    public void testAdd() {

        // Given
        given(calcService.add(20.0, 10.0)).willAnswer((Answer<Double>) invocation -> {

            //get the arguments passed to mock
            Object[] args = invocation.getArguments();

            //get the mock
            Object mock = invocation.getMock();

            //return the result
            return 30.0;
        });

        // When
        double expected = mathApplication.add(20.0, 10.0);

        // Then
        then(calcService).should(times(1)).add(20.0,10.0);
        assertThat(expected, closeTo(30.0, 0.0));
    }


}
