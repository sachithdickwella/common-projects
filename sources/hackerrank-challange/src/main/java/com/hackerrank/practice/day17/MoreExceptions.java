package com.hackerrank.practice.day17;

import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class MoreExceptions {

    public static void main(String[] args) {

        try (Scanner in = new Scanner(System.in)) {
            int t = in.nextInt();
            while (t-- > 0) {

                int n = in.nextInt();
                int p = in.nextInt();
                Calculator myCalculator = new Calculator();
                try {
                    int ans = myCalculator.power(n, p);
                    System.out.println(ans);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}

class Calculator {

    int power(int n, int p) {
        if (n < 0 || p < 0) {
            throw new NumberFormatException("n and p should be non-negative");
        }

        return (int)Math.pow(n, p);
    }
}
