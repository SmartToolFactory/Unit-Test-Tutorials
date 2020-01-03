package com.smarttoolfactory.tutorial2_1mockito;

import com.smarttoolfactory.tutorial2_1mockito.model_math_application.CalculatorService;
import com.smarttoolfactory.tutorial2_1mockito.model_math_application.MathApplication;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class Test1WithMock2Setup {


    MathApplication mathApplication;


    CalculatorService calcService;


    @Before
    public void setUp() {

        mathApplication = new MathApplication();

        // 🔥 Creates mock object of calcService
        calcService = mock(CalculatorService.class);

        mathApplication.setCalculatorService(calcService);

    }

    @Test
    public void testAdd() {

        // 🔥 calcService is an Interface
        //add the behavior of calc service to add two numbers
        when(calcService.add(10.0, 20.0)).thenReturn(30.00);

        System.out.println("testAddWithAnswer calcService: " + calcService + ", mathApplication: " + mathApplication);


        //test the add functionality
        Assert.assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);

    }


    @Test
    public void testSubtract() {

        when(calcService.subtract(20.0, 10.0)).thenReturn(10.00);

        System.out.println("testAddWithAnswer calcService: " + calcService + ", mathApplication: " + mathApplication);

        //test the add functionality
        Assert.assertEquals(mathApplication.subtract(20.0, 10.0), 10.0, 0);

    }
}