package com.hackerrank.challange.alg30;

import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class Staircase {

    private static void staircase(int n) {
        int t = n;
        while (t-- > 0) {
            System.out.print(String.format("%" + (t > 0 ? t : "")  + "s", ""));
            for (int iItr = 0; iItr < n - t; iItr++) {
                System.out.print("#");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            staircase(n);
        }
    }
}
