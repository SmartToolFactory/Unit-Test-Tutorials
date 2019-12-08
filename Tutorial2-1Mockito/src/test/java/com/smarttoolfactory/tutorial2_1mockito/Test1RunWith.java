package com.smarttoolfactory.tutorial2_1mockito;

import com.smarttoolfactory.tutorial2_1mockito.model1.CalculatorService;
import com.smarttoolfactory.tutorial2_1mockito.model1.MathApplication;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * When and thenReturn is used to add behavior to an non-concrete class
 */
// @RunWith attaches a runner with the test class to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class Test1RunWith {

    //@InjectMocks annotation is used to create and inject the mock object
    // 🔥 A NEW object is created for each test
    @InjectMocks
    MathApplication mathApplication;

    //@Mock annotation is used to create the mock object to be injected
    @Mock
    CalculatorService calcService;

    @Test
    public void testAdd() {

        // 🔥 calcService is an Interface
        //add the behavior of calc service to add two numbers
        when(calcService.add(10.0, 20.0)).thenReturn(30.00);

        System.out.println("testAdd calcService: " + calcService + ", mathApplication: " + mathApplication);

        //test the add functionality
        Assert.assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);

    }


    @Test
    public void testSubtract() {

        when(calcService.subtract(20.0, 10.0)).thenReturn(10.00);

        System.out.println("testAdd calcService: " + calcService + ", mathApplication: " + mathApplication);

        //test the add functionality
        Assert.assertEquals(mathApplication.subtract(20.0, 10.0), 10.0, 0);

    }
}