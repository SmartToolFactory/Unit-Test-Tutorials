package com.smarttoolfactory.tutorial3_1_junit5.utils;

public class StringUtils {

    public static boolean isPalindrome(String input) {
        StringBuilder stringBuilder = new StringBuilder(input);
        StringBuilder reverse = stringBuilder.reverse();
        return stringBuilder.toString().equals(reverse.toString());
    }

}
