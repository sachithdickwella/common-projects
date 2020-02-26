package com.hackerrank.challange.alg29;

import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class PlusMinus {

    private static void plusMinus(int[] arr) {
        int pCount = 0, mCount = 0, nCount = 0;
        for (int i : arr) {
            if (i > 0) {
                pCount++;
            } else if (i < 0) {
                mCount++;
            } else {
                nCount++;
            }
        }

        int ln = arr.length;
        System.out.format("%.6f%n%.6f%n%.6f%n",
                ((double)pCount / ln),
                ((double)mCount / ln),
                ((double)nCount / ln));
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] arr = new int[n];

            String[] arrItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int arrItem = Integer.parseInt(arrItems[i]);
                arr[i] = arrItem;
            }

            plusMinus(arr);
        }
    }
}
