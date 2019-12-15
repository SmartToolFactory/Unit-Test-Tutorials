package com.smarttoolfactory.tutorial2_2mockito_bdd;

import com.smarttoolfactory.tutorial2_2mockito_bdd.model_math_application.CalculatorService;
import com.smarttoolfactory.tutorial2_2mockito_bdd.model_math_application.MathApplication;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class Test5Spying {

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


        // Given
        // 🔥🔥 Adding stub FAILS the test with UnnecessaryStubbingException
//        given(calcService.add(10.0, 20.0)).willReturn(30.00);

        // When
        double expected = mathApplication.add(20.0, 10.0);

        // Then
        assertThat(expected, closeTo(30.0, 0.0));
        // Both PASS if invoked one at a time
        then(calcService).should(times(1)).add(anyDouble(), anyDouble());
//        then(calcService).should(times(1)).add(10.0, 10.0);

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