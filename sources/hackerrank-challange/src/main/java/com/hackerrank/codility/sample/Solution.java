package com.hackerrank.codility.sample;

import java.util.Arrays;

/**
 * @author Sachith Dickwella
 */
public class Solution {

    public static void main(String[] args) {

    }

    public int solution2(String S) {
        String[] words = S.split("[.?!]");

        return Arrays.stream(words)
                .map(String::trim)
                .mapToInt(sentence -> sentence.split("\\s+").length)
                .max()
                .orElse(0);
    }

    public static int solution1(int A, int B) {

        int count = 0;

        for (int i = A; i <= B; i++) {
            double sqrt = Math.sqrt(i);
            if (sqrt % (int) sqrt == 0) {
                count++;
            }
        }
        return count;
    }
}