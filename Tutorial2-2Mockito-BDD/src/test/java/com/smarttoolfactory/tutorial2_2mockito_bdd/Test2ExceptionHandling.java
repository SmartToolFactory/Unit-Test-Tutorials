package com.smarttoolfactory.tutorial2_2mockito_bdd;

import com.smarttoolfactory.tutorial2_2mockito_bdd.model_math_application.CalculatorService;
import com.smarttoolfactory.tutorial2_2mockito_bdd.model_math_application.MathApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.fail;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class Test2ExceptionHandling {

    @InjectMocks
    MathApplication mathApplication;

    @Mock
    CalculatorService calcService;

    @Test
    public void testAdd_throws_RuntimeException() {

        // GIVEN
//        given(calcService.add(10.0, 20.0)).willThrow(new RuntimeException("Add operation not implemented"));
        // or
        willThrow(new RuntimeException("Add operation not implemented"))
                .given(calcService).add(10.0, 20.0);


        // WHEN
        try {
            mathApplication.add(10.0, 20.0);

            // THEN
            // expecting exception - should jump to catch block, skipping the line below:
            fail("Should have thrown RuntimeException");
        } catch (RuntimeException e) {
            // expected exception, so no failure
            System.out.println("RunTime Exception is thrown");
        }

        then(calcService).should(times(1)).add(10.0, 20.0);

    }
}