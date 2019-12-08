package com.smarttoolfactory.tutorial2_1mockito;

import com.smarttoolfactory.tutorial2_1mockito.model1.CalculatorService;
import com.smarttoolfactory.tutorial2_1mockito.model1.MathApplication;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Test3ExceptionHandling {

    @InjectMocks
    MathApplication mathApplication;

    @Mock
    CalculatorService calcService;

    @Test(expected = RuntimeException.class)
    public void testAdd() {

        //add the behavior to throw exception

        when(calcService.add(10.0, 20.0)).thenThrow(new RuntimeException("Add operation not implemented"));

        //test the add functionality
        Assert.assertEquals(mathApplication.add(10.0, 20.0), 30.0, 0);
    }
}