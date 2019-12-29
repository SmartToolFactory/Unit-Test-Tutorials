package com.mkyong.timeout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTimeout;

public class TimeOutExample2 {

    // timed out after 5 seconds
    @Test
    void test_timeout_fail() {
        // assertTimeout(Duration.ofSeconds(5), () -> delaySecond(10)); // this will fail
        assertTimeout(Duration.ofSeconds(5), () -> delaySecond(1));
    }

    void delaySecond(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
