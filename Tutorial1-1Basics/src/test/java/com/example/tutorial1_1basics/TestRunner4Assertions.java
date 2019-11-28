package com.example.tutorial1_1basics;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner4Assertions {

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(TestJunit4Assertions.class);

        for (Failure failure : result.getFailures()) {
            System.out.println("TestRunner4Assertions failure: " + failure.toString());
        }

        System.out.println("TestRunner4Assertions result successful: " + result.wasSuccessful());

    }

}
