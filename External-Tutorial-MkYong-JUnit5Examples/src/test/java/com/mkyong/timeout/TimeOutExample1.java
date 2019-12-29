package com.mkyong.timeout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

public class TimeOutExample1 {

    // timed out after 5 seconds
    @BeforeEach
    @Timeout(5)
    void setUpDB() throws InterruptedException {
        //TimeUnit.SECONDS.sleep(10);
    }

    // timed out after 500 miliseconds
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void test_this() {
    }

}
