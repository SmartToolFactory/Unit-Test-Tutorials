package com.smarttoolfactory.tutorial3_1_junit5;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Test8DynamicTests {

    @TestFactory
    Collection<DynamicTest> dynamicTest() {

        return Arrays.asList(
                DynamicTest.dynamicTest("Test1",
                        () -> assertEquals(4, 2 + 2)),
                DynamicTest.dynamicTest(("Test2"),
                        () -> assertEquals(2, 4 - 2))
        );
    }


    /**
     * The @TestFactory method tells JUnit that this is a factory for creating dynamic tests.
     * As we can see, we're only returning a Collection of DynamicTest.
     * Each of the DynamicTest consists of two parts, the name of the test or the display name, and an Executable.
     *
     * @return collection of dynamic test
     */
    @TestFactory
    Collection<DynamicTest> dynamicTestsWithCollection() {
        return Arrays.asList(

                DynamicTest.dynamicTest("Add test",
                        () -> assertEquals(2, Math.addExact(1, 1))),

                DynamicTest.dynamicTest("Multiply Test",
                        () -> assertEquals(4, Math.multiplyExact(2, 2)))
        );
    }

    /**
     * Dynamic test that returns {@link Iterable}
     */
    @TestFactory
    Iterable<DynamicTest> dynamicTestsWithIterable() {
        return Arrays.asList(
                DynamicTest.dynamicTest("Add test",
                        () -> assertEquals(2, Math.addExact(1, 1))),
                DynamicTest.dynamicTest("Multiply Test",
                        () -> assertEquals(4, Math.multiplyExact(2, 2))));
    }


    /**
     * Dynamic test that returns {@link Iterator}
     */
    @TestFactory
    Iterator<DynamicTest> dynamicTestsWithIterator() {
        return Arrays.asList(
                DynamicTest.dynamicTest("Add test",
                        () -> assertEquals(2, Math.addExact(1, 1))),
                DynamicTest.dynamicTest("Multiply Test",
                        () -> assertEquals(4, Math.multiplyExact(2, 2))))
                .iterator();
    }

    /**
     * Dynamic test that returns {@link Stream}
     */
    @TestFactory
    Stream<DynamicTest> dynamicTestsFromIntStream() {
        return IntStream.iterate(0, n -> n + 2).limit(10)
                .mapToObj(n -> DynamicTest.dynamicTest("test" + n,
                        () -> assertTrue(n % 2 == 0)));
    }


}
