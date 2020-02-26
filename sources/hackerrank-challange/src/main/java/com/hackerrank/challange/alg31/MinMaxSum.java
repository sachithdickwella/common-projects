package com.hackerrank.challange.alg31;

import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class MinMaxSum {

    private static void miniMaxSum(int[] arr) {
        long min = 0, max = 0;
        for (int i = 0; i < arr.length; i++) {
            long rotation = 0;
            for (int x = 0; x < arr.length; x++) {
                if (x != i) {
                    rotation += arr[x];
                }
            }

            if (min == 0 || min > rotation) {
                min = rotation;
            }

            if (max == 0 || max < rotation) {
                max = rotation;
            }
        }

        System.out.format("%d %d%n", min, max);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] arr = new int[] {1, 2, 3, 4, 5};

            String[] arrItems = scanner.nextLine().split("\\s");
            for (int i = 0; i < 5; i++) {
                int arrItem = Integer.parseInt(arrItems[i]);
                arr[i] = arrItem;
            }
            miniMaxSum(arr);
        }
    }
}
