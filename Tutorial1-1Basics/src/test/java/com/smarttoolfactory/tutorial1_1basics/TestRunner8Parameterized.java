package com.smarttoolfactory.tutorial1_1basics;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner8Parameterized {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestJunit8Parameterized.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}