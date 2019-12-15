package com.smarttoolfactory.tutorial2_2mockito_bdd;


import com.smarttoolfactory.tutorial2_2mockito_bdd.model_math_application.CalculatorService;
import com.smarttoolfactory.tutorial2_2mockito_bdd.model_math_application.MathApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class Test1Basics {

    @InjectMocks
    MathApplication mathApplication;

    @Mock
    CalculatorService calcService;


    @Test
    public void testAdd() {

//        // 🔥 calcService is an Interface
//        //add the behavior of calc service to add two numbers
//        when(calcService.add(10.0, 20.0)).thenReturn(30.00);
//
//        System.out.println("testAdd calcService: " + calcService + ", mathApplication: " + mathApplication);
//
//        //test the add functionality
//        Assert.assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);

        // Given
        given(calcService.add(10.0, 20.0)).willReturn(30.00);

        // When
        double expected = mathApplication.add(10.0, 20.0);

        // Then
        // Check if adding 10 + 20  with add method is close to 30 with ERROR 0
        assertThat(expected, closeTo(30.0, 0.0));
        then(calcService).should(times(1)).add(10.0, 20.0);

    }


}
