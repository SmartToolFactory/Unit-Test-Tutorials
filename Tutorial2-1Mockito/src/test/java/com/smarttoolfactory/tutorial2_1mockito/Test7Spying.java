package com.smarttoolfactory.tutorial2_1mockito;

import com.smarttoolfactory.tutorial2_1mockito.model_math_application.CalculatorService;
import com.smarttoolfactory.tutorial2_1mockito.model_math_application.MathApplication;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class Test7Spying {

    private MathApplication mathApplication;
    private CalculatorService calcService;

    @Before
    public void setUp() {
        mathApplication = new MathApplication();
        Calculator calculator = new Calculator();

        // 🔥 We use real object
        calcService = spy(calculator);
        mathApplication.setCalculatorService(calcService);
    }

    @Test
    public void testAdd() {

        //perform operation on real object
        //test the add functionality
        // 🔥 This assert passes since it's implemented and correct
        Assert.assertEquals(mathApplication.add(20.0, 10.0), 30.0, 0);

        // 🔥 This assert fails since it's not implemented yet
        Assert.assertEquals(mathApplication.subtract(20.0, 10.0), 10.0, 10.0);
    }

    class Calculator implements CalculatorService {
        @Override
        public double add(double input1, double input2) {
            return input1 + input2;
        }

        @Override
        public double subtract(double input1, double input2) {
            throw new UnsupportedOperationException("Method not implemented yet!");
        }

        @Override
        public double multiply(double input1, double input2) {
            throw new UnsupportedOperationException("Method not implemented yet!");
        }

        @Override
        public double divide(double input1, double input2) {
            throw new UnsupportedOperationException("Method not implemented yet!");
        }
    }
}