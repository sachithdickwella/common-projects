package com.hackerrank.challange.alg35;

import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class AbsoluteDifferent {

    private static int minimumAbsoluteDifference(int[] arr) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i ++) {
            for (int j = i + 1; j < arr.length; j++) {
                int cm = Math.abs(arr[i] - Math.abs(arr[j]));
                if (cm < min) min = cm;
            }
        }
        return min;
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

            int result = minimumAbsoluteDifference(arr);
            System.out.print(result);
        }
    }
}
