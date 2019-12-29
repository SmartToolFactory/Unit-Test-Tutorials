package com.mkyong.conditional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.util.Map;

// https://www.mkyong.com/java/java-how-to-display-all-environment-variable/
public class EnvVariableTest {

    @Test
    @EnabledIfEnvironmentVariable(named = "PROCESSOR_IDENTIFIER", matches = ".*Intel64 Family 6.*")
    void onIntel64() {
        System.out.println("Run this on Intel6 Family 6 only.");
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "8")
    void onProcessor8() {
        System.out.println("Run this if 8 processors.");
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CURRENT_ENV", matches = ".*development.*")
    void notOnDeveloperPC() {
        System.out.println("Do not run this if env variables 'CURRENT_ENV' matches .*development.* ");
    }

    @Disabled("print environment variables.")
    @Test
    void printEnvironmentProperties() {
        Map<String, String> env = System.getenv();
        env.forEach((k, v) -> System.out.println(k + ":" + v));
    }


}
