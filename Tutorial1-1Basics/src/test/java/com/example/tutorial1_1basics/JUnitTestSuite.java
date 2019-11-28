package com.example.tutorial1_1basics;

import junit.framework.TestResult;
import junit.framework.TestSuite;

/**
 * TestSuite adds multiple test to run
 */
public class JUnitTestSuite {

    public static void main(String[] a) {
        // add the test's in the suite
        TestSuite suite = new TestSuite(TestJunit1.class, TestJunit2TestCase.class, TestJunit3TestResult.class );
        TestResult result = new TestResult();
        suite.run(result);
        System.out.println("Number of test cases = " + result.runCount());
    }
}