package com.mkyong.repeated;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepeatedSample3Test {

    @RepeatedTest(3)
    void math_add_4(RepetitionInfo repetitionInfo) {
        System.out.println("Repetition #" + repetitionInfo.getCurrentRepetition());
        assertEquals(3, repetitionInfo.getTotalRepetitions());
    }

}