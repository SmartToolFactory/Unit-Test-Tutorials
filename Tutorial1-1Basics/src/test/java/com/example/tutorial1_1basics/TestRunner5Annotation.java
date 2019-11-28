package com.example.tutorial1_1basics;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner5Annotation {

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(TestJunit5Annotation.class);


        for (Failure failure : result.getFailures()) {
            System.out.println("TestRunner5Annotation failure: " + failure.toString());
        }

        System.out.println("TestRunner5Annotation result successful: " + result.wasSuccessful());

    }
}
