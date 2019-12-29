package com.smarttoolfactory.tutorial2_1mockito;

import com.smarttoolfactory.tutorial2_1mockito.model_math_application.CalculatorService;
import com.smarttoolfactory.tutorial2_1mockito.model_math_application.MathApplication;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class Test2Verify {

    @InjectMocks
    MathApplication mathApplication = new MathApplication();

    @Mock
    CalculatorService calcService;

    @Test
    public void testAdd() {

        //add the behavior of calc service to add two numbers
        when(calcService.add(10.0, 20.0)).thenReturn(30.00);

        MathApplication mathApplication1 = new MathApplication();
        MathApplication mathApplication2 = new MathApplication();

        System.out.println("Mat1 equals Mat2: " + mathApplication1.equals(mathApplication));
        System.out.println("Mat1 == Mat2: " + (mathApplication1 == mathApplication2));


        //test the add functionality
        Assert.assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);
        Assert.assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);
        Assert.assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);

        //add the behavior of calc service to subtract two numbers
        when(calcService.subtract(20.0, 10.0)).thenReturn(10.00);

        double expected = mathApplication.subtract(20.0, 10.0);

        //test the subtract functionality
        assertEquals(expected, 10.0, 0.0);


        //default call count is 1
        verify(calcService).subtract(20.0, 10.0);

        //check if add function is called three times
        verify(calcService, times(3)).add(10.0, 20.0);

        //verify that method was never called on a mock
        verify(calcService, never()).multiply(10.0, 20.0);

    }
}