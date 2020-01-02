package com.smarttoolfactory.tutorial4_1kotlin.model_calculation

import java.util.*

class Calculation {

    //method that returns maximum number
    fun findMax(arr: IntArray): Int {
        var max = arr[0] //arr[0] instead of 0
        for (i in 1 until arr.size) {
            if (max < arr[i]) max = arr[i]
        }
        return max
    }

    //method that returns cube of the given number
    fun cube(n: Int): Int {
        return n * n * n
    }

    //method that returns reverse words
    fun reverseWord(str: String): String {
        val result = StringBuilder()
        val tokenizer = StringTokenizer(str, " ")
        while (tokenizer.hasMoreTokens()) {
            val sb = StringBuilder()
            sb.append(tokenizer.nextToken())
            sb.reverse()
            result.append(sb)
            if (result.length != str.length) {
                result.append(" ")
            }
        }
        return result.toString()
    }
}