package com.smarttoolfactory.tutorial1_1basics;

import org.junit.After;
import org.junit.AfterClass;

import org.junit.Before;
import org.junit.BeforeClass;

import org.junit.Ignore;
import org.junit.Test;


/**
 *
 * Each time a test is run, new Instance of this Test class is created.
 *
 * <p></p>
 * <li>
 * 1- '@Test' The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case.
 * </li>
 * <li>
 * 2- '@Before' Several tests need similar objects created before they can run. Annotating a public void method with @Before causes that method to be run before each Test method.
 * </li>
 * <li>
 * 3- '@After' If you allocate external resources in a Before method, you need to release them after the test runs. Annotating a public void method with @After causes that method to be run after the Test method.
 * </li>
 * <li>
 * 4- '@BeforeClass' Annotating a public static void method with @BeforeClass causes it to be run once before any of the test methods in the class.
 * </li>
 * <li>
 * 5- '@AfterClass' This will perform the method after all tests have finished. This can be used to perform clean-up activities.
 * </li>
 * <li>
 * 6- '@Ignore' The Ignore annotation is used to ignore the test and that test will not be executed.
 * </li>
 */
public class TestJunit5ExecutionOrder {

    //execute before class
    @BeforeClass
    public static void beforeClass() {
        System.out.println("in before class");
    }

    //execute after class
    @AfterClass
    public static void afterClass() {
        System.out.println("in after class");
    }

    //execute before test
    @Before
    public void before() {
        System.out.println("in before with instance: " + this);
    }

    //execute after test
    @After
    public void after() {
        System.out.println("in after");
    }

    //test case
    @Test
    public void test1() {
        System.out.println("in test1");
    }

    //test case
    @Test
    public void test2() {
        System.out.println("in test2");
    }

    //test case ignore and will not execute
    @Ignore
    public void ignoreTest() {
        System.out.println("in ignore test");
    }
}