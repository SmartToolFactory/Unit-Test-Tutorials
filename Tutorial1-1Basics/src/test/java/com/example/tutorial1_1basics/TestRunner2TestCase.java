package com.example.tutorial1_1basics;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner2TestCase {

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(TestJunit2TestCase.class);

        for (Failure failure : result.getFailures()) {
            System.out.println("TestRunner2TestCase failure: " + failure.toString());
        }

        System.out.println("TestRunner2TestCase result successful: " + result.wasSuccessful());
    }
}