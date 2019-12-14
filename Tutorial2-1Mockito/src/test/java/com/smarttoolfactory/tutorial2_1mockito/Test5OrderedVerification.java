package com.smarttoolfactory.tutorial2_1mockito;

import com.smarttoolfactory.tutorial2_1mockito.model_math_application.CalculatorService;
import com.smarttoolfactory.tutorial2_1mockito.model_math_application.MathApplication;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Test5OrderedVerification {

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

        //add the behavior to add numbers
        when(calcService.add(20.0, 10.0)).thenReturn(30.0);

        //subtract the behavior to subtract numbers
        when(calcService.subtract(20.0, 10.0)).thenReturn(10.0);

        //test the add functionality
        Assert.assertEquals(mathApplication.add(20.0, 10.0), 30.0, 0);

        //test the subtract functionality
        Assert.assertEquals(mathApplication.subtract(20.0, 10.0), 10.0, 0);

        //create an inOrder verifier for a single mock
        InOrder inOrder = inOrder(calcService);

        // 🔥🔥 following will make sure that add is first called then subtract is called.
        inOrder.verify(calcService).subtract(20.0, 10.0);
        inOrder.verify(calcService).add(20.0, 10.0);

        /*
            Prints:

            testAddAndSubtract(MathApplicationTester):
            Verification in order failure
            Wanted but not invoked:
            calculatorService.add(20.0, 10.0);
            -> at MathApplicationTester.testAddAndSubtract(MathApplicationTester.java:48)
            Wanted anywhere AFTER following interaction:
            calculatorService.subtract(20.0, 10.0);
            -> at MathApplication.subtract(MathApplication.java:13)
            false
         */

    }
}