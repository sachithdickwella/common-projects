package com.hackerrank.google;

import java.util.Arrays;

/**
 * @author Sachith Dickwella
 */
public class SlidingWindow {

    private static int findMaxOfGivenNumberOfConsecutiveValues(int[] values, int block) {
        int nextBlockSum = Arrays.stream(Arrays.copyOfRange(values, 0, block)).sum();

        int max = nextBlockSum;
        for (int i = 1; i <= values.length - block; i++) {
            nextBlockSum = nextBlockSum - values[i - 1] + values[i + (block - 1)];
            if (nextBlockSum > max) max = nextBlockSum;
        }

        return max;
    }

    public static void main(String[] args) {
        System.out.println(
                findMaxOfGivenNumberOfConsecutiveValues(
                        new int[]{1, 2, 9, 4, 5, 7, 9, 3, 2, 7, 8, 3},
                        5
                ));
    }
}
