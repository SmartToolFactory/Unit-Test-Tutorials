package com.example.tutorial1_1basics;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Either use '@Rule' with ExpectedException or user '@Test(expected = ...Exception.class)'
 * to test for metho that should throw a required exception
 */
public class Test4RuleException {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    //    @Test(expected = IllegalArgumentException.class)
    @Test
    public void throwsIllegalArgumentExceptionIfIconIsNull() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Negative value not allowed");
        ClassToBeTested testClass = new ClassToBeTested();
        testClass.methodToBeTest(-1);
    }


    class ClassToBeTested {

        public void methodToBeTest(int data) {
            if (data < 0) {
                throw new IllegalArgumentException("Negative value not allowed");
            }
        }


    }
}