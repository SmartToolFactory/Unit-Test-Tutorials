package com.smarttoolfactory.tutorial3_1_junit5.model_calculation;

import java.util.StringTokenizer;

public class Calculation {

    //method that returns maximum number
    public int findMax(int arr[]) {
        int max = arr[0];//arr[0] instead of 0
        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i])
                max = arr[i];
        }
        return max;
    }

    //method that returns cube of the given number
    public int cube(int n) {
        return n * n * n;
    }

    //method that returns reverse words
    public String reverseWord(String str) {

        StringBuilder result = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(str, " ");

        while (tokenizer.hasMoreTokens()) {
            StringBuilder sb = new StringBuilder();
            sb.append(tokenizer.nextToken());
            sb.reverse();

            result.append(sb);

            if (result.length() != str.length()) {
                result.append(" ");
            }

        }
        return result.toString();
    }
}