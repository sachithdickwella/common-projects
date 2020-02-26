package com.hackerrank.challange.alg34;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class CoinChangeProblem {

    private static long getWays(long n, @NotNull long[] c) {
        long ways = 0;
        for (int a = 0; a < c.length; a++) {

        }

        return 0;
    }

    public static void main(String[] args) {

       try(Scanner scanner = new Scanner(System.in)) {
           String[] nm = scanner.nextLine().split(" ");

           int n = Integer.parseInt(nm[0]);

           int m = Integer.parseInt(nm[1]);

           long[] c = new long[m];

           String[] cItems = scanner.nextLine().split(" ");
           scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

           for (int i = 0; i < m; i++) {
               long cItem = Long.parseLong(cItems[i]);
               c[i] = cItem;
           }

           long ways = getWays(n, c);
           System.out.println(ways);
       }
    }
}
