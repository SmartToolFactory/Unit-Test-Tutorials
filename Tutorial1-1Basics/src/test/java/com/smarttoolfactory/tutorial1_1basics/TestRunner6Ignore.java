package com.smarttoolfactory.tutorial1_1basics;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner6Ignore {

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(TestJunit6Ignore.class);

        for (Failure failure : result.getFailures()) {
            System.out.println("TestRunner6Ignore failure: " + failure.toString());
        }

        System.out.println("TestRunner6Ignore result successful: " + result.wasSuccessful());
    }
}
