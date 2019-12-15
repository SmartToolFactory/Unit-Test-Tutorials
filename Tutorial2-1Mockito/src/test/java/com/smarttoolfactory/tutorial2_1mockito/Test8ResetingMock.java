package com.smarttoolfactory.tutorial2_1mockito;

import com.smarttoolfactory.tutorial2_1mockito.model_math_application.CalculatorService;
import com.smarttoolfactory.tutorial2_1mockito.model_math_application.MathApplication;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.reset;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Test8ResetingMock {

    private MathApplication mathApplication;
    private CalculatorService calcService;

    @Before
    public void setUp(){
        mathApplication = new MathApplication();
        calcService = mock(CalculatorService.class);
        mathApplication.setCalculatorService(calcService);
    }

    @Test
    public void testAddAndSubtract(){

        //add the behavior to add numbers
        when(calcService.add(20.0,10.0)).thenReturn(30.0);

        //test the add functionality
        Assert.assertEquals(mathApplication.add(20.0, 10.0),30.0,0);

        //reset the mock
        reset(calcService);

        /*
         🔥 FAILs because mock is reset and when function, should be called again
            when(calcService.add(20.0,10.0)).thenReturn(30.0);

         */

        //test the add functionality after resetting the mock
        Assert.assertEquals(mathApplication.add(20.0, 10.0),30.0,0);
    }
}