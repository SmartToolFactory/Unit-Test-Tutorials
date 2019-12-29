package com.mkyong.conditional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.util.Properties;

// https://www.mkyong.com/java/how-to-list-all-system-properties-key-and-value-in-java/
public class SystemPropertyTest {

    @Test
    @EnabledIfSystemProperty(named = "java.vm.name", matches = ".*OpenJDK.*")
    void onOpenJDK() {
        System.out.println("Run this on OpenJDK!");
    }

    @Test
    @DisabledIfSystemProperty(named = "user.country", matches = "MY")
    void notOnCountryMalaysia() {
        System.out.println("Do not run this on country code MY");
    }

    @Disabled("print system properties.")
    @Test
    void printSystemProperties() {
        Properties properties = System.getProperties();
        properties.forEach((k, v) -> System.out.println(k + ":" + v));
    }

}
