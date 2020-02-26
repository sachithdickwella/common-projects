package com.hackerrank.ds.arrays;

import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class ReverseArrays {

    private static int[] reverse(int[] array) {
        int[] out = new int[array.length];
        for (int idx = 0; idx < array.length; idx++) {
            out[idx] = array[(array.length - 1) - idx];
        }
        return out;
    }

    public static void main(String[] args) {
        try (final Scanner scanner = new Scanner(System.in)) {
            int arrCount = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] arr = new int[arrCount];

            String[] arrItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < arrCount; i++) {
                int arrItem = Integer.parseInt(arrItems[i]);
                arr[i] = arrItem;
            }

            int[] res = reverse(arr);

            for (int i = 0; i < res.length; i++) {
                System.out.print(res[i]);
                if (i != res.length - 1) System.out.print(" ");
            }
        }
    }
}
