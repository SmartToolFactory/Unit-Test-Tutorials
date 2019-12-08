package com.smarttoolfactory.tutorial1_1basics;


import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * '@Ignore' causes test class to be ignored entirely
 */
@Ignore
public class TestJunit7Ignore {

    String message = "Robert";
    MessageUtil messageUtil = new MessageUtil(message);

    @Test
    public void testPrintMessage() {
        System.out.println("Inside testPrintMessage()");
        message = "Robert";
        assertEquals(message,messageUtil.printMessage());
    }

    @Test
    public void testSalutationMessage() {
        System.out.println("Inside testSalutationMessage()");
        message = "Hi!" + "Robert";
        assertEquals(message,messageUtil.salutationMessage());
    }

}
