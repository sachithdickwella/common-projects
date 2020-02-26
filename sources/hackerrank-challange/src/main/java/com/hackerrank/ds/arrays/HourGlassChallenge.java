package com.hackerrank.ds.arrays;

import org.jetbrains.annotations.NotNull;

/**
 * @author Sachith Dickwella
 */
public class HourGlassChallenge {

    public static void main(String[] args) {
        int[][] data = new int[][]{
                {1, 1, 1, 0, 0, 0},
                {0, 1, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 0},
                {0, 0, 2, 4, 4, 0},
                {0, 0, 0, 2, 0, 0},
                {0, 0, 1, 2, 4, 0}
        };

        System.out.println(hourglassSum(data));
    }

    private static int hourglassSum(@NotNull int[][] array) {
        int max = Integer.MIN_VALUE;
        for (int y = 0; y < array.length; y++) {
            for (int x = 0; x < array.length; x++) {
                int temp = traversH(y, x, array);
                if (temp > max) {
                    max = temp;
                }
            }
        }
        return max;
    }

    private static int traversH(int y, int x, @NotNull int[][] data) {
        if (y + 2 < data.length && x + 2 < data[y].length) {
            int depth = 0;
            int sum = 0;
            for (int a = y; a < y + 3; a++) {
                for (int b = x; b < x + 3; b++) {
                    if (depth != 3 && depth != 5) {
                        sum += data[a][b];
                    }
                    depth++;
                }
            }
            return sum;
        }
        return Integer.MIN_VALUE;
    }
}
