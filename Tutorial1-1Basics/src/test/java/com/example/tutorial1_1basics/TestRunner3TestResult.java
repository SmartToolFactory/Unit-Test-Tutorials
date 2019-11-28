package com.example.tutorial1_1basics;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner3TestResult {

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(TestJunit3TestResult.class);

        for (Failure failure : result.getFailures()) {
            System.out.println("TestRunner3TestResult failure: " + failure.toString());
        }

        System.out.println("TestRunner3TestResult result successful: " + result.wasSuccessful());
    }
}