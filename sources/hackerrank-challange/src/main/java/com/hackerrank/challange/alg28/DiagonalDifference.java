package com.hackerrank.challange.alg28;

import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class DiagonalDifference {

    private static int diagonalDifference(int[][] arr) {
        int pDiagonal = 0, sDiagonal = 0, al = arr.length;
        for (int d = 0; d < al; d++) {
            pDiagonal += arr[d][d];
            sDiagonal += arr[d][(al - 1) - d];
        }
        int dDiff = pDiagonal - sDiagonal;
        return dDiff < 0 ? dDiff * -1 : dDiff;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[][] arr = new int[n][n];

            for (int i = 0; i < n; i++) {
                String[] arrRowItems = scanner.nextLine().split("\\s");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < n; j++) {
                    int arrItem = Integer.parseInt(arrRowItems[j]);
                    arr[i][j] = arrItem;
                }
            }

            int result = diagonalDifference(arr);
            System.out.println(result);
        }
    }
}
