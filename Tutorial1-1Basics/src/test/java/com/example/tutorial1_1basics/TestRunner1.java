package com.example.tutorial1_1basics;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


// TODO: Not Working ???
public class TestRunner1 {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestJunit1.class);

        for (Failure failure : result.getFailures()) {
            System.out.println("TestRunner1 failure: " + failure.toString());
        }

        System.out.println("TestRunner1 result successful: " + result.wasSuccessful());
    }
}