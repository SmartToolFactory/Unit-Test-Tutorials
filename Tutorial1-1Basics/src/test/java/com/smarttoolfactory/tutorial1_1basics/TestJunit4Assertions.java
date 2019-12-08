package com.smarttoolfactory.tutorial1_1basics;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Test class to demonstrate assertion types
 *
 * <p></p>
 *
 * <li>1- <strong>void assertEquals(boolean expected, boolean actual)</strong>
 * Checks that two primitives/objects are equal.
 * </li>
 * <li>2- <strong>void assertTrue(boolean condition)</strong>
 * <p>
 * Checks that a condition is true.
 * </li>
 * <li> 3- <strong>void assertFalse(boolean condition)</strong>
 * <p>
 * Checks that a condition is false.
 * </li>
 * <li>4- <strong>void assertNotNull(Object object)</strong>
 * <p>
 * Checks that an object isn't null.
 * </li>
 * <li>5-  <strong>void assertNull(Object object)</strong>
 * <p>
 * Checks that an object is null.
 * </li>
 * <li>6- <strong>void assertSame(object1, object2)</strong>
 * <p>
 * The assertSame() method tests if two object references point to the same object.
 * </li>
 * <li>7- <strong>void assertNotSame(object1, object2)</strong>
 * <p>
 * The assertNotSame() method tests if two object references do not point to the same object.
 * </li>
 * <li> <strong>8- void assertArrayEquals(expectedArray, resultArray)</strong>
 * <p>
 * The assertArrayEquals() method will test whether two arrays are equal to each other.
 */
public class TestJunit4Assertions {

    @Test
    public void testAssertions() {
        //test data
        String str1 = new String("abc");
        String str2 = new String("abc");
        String str3 = null;
        String str4 = "abc";
        String str5 = "abc";

        int val1 = 5;
        int val2 = 6;

        String[] expectedArray = {"one", "two", "three"};
        String[] resultArray = {"one", "two", "three"};

        //Check that two objects are equal
        assertEquals(str1, str2);

        //Check that a condition is true
        assertTrue(val1 < val2);

        //Check that a condition is false
        assertFalse(val1 > val2);

        //Check that an object isn't null
        assertNotNull(str1);

        //Check that an object is null
        assertNull(str3);

        //Check if two object references point to the same object
        assertSame(str4, str5);

        //Check if two object references not point to the same object
        assertNotSame(str1, str3);

        //Check whether two arrays are equal to each other.
        assertArrayEquals(expectedArray, resultArray);
    }
}