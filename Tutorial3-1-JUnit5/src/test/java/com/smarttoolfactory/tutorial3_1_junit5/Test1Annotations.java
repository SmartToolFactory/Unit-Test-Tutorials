package com.smarttoolfactory.tutorial3_1_junit5;


import com.smarttoolfactory.tutorial3_1_junit5.model_calculation.Calculation;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * <li>@Before annotation is renamed to @BeforeEach</li>
 * <li>@After annotation is renamed to @AfterEach</li>
 * <li>@BeforeClass annotation is renamed to @BeforeAll</li>
 * <li>@AfterClass annotation is renamed to @AfterAll</li>
 * <li>@Ignore annotation is renamed to @Disabled</li>
 */
public class Test1Annotations {
    Calculation mCalculation;

    //    @BeforeClass
    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        System.out.println("before all");
    }

    //    @Before
    @BeforeEach
    public void setUp() throws Exception {
        mCalculation = new Calculation();
        System.out.println("before each");
    }

    @Test
    public void testInitial() {
        System.out.println("Test Initial");
        assertEquals("Hello", new String("Hello"));
        assertEquals(new String("Hello"), new String("Hello"));
    }

    @Test
    public void testCube() {
        System.out.println("Test case cube");
        assertEquals(27, mCalculation.cube(3));
    }

    @Test
    public void testFindMax() {
        System.out.println("Test case find max");
        assertEquals(4, mCalculation.findMax(new int[]{1, 3, 4, 2}));
        assertEquals(-2, mCalculation.findMax(new int[]{-12, -3, -4, -2}));
    }

    @Test
    public void testReverseWord() {
        System.out.println("Test case reverse word");
        assertEquals("ym eman si nahk", mCalculation.reverseWord("my name is khan"));
    }


    //    @After
    @AfterEach
    public void tearDown() throws Exception {
        System.out.println("after each");
    }

    //    @AfterClass
    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        System.out.println("after all");
    }

}