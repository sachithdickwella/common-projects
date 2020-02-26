package com.hackerrank.practice.day20;

import java.util.Scanner;

/**
 * Problem link is : <link>https://www.hackerrank.com/challenges/30-sorting/problem?h_r=next-challenge&h_v=zen</link>
 *
 * @author Sachith Dickwella
 */
public class Sorting {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n];
        for(int a_i=0; a_i < n; a_i++){
            a[a_i] = in.nextInt();
        }

        Sorting.sort(a);
    }

    private static void sort(int[] a) {
        int numberOfSwaps = 0;
        for (int i = 0; i < a.length; i++) {
            int nxt;
            for (int x = 0; x < a.length - 1; x++) {
                if (a[x] > a[x + 1]) {
                    nxt = a[x + 1];
                    a[x + 1] = a[x];
                    a[x] = nxt;

                    numberOfSwaps++;
                }
            }
        }

        System.out.format("Array is sorted in %d swaps.%n", numberOfSwaps);
        System.out.format("First Element: %d%n", a[0]);
        System.out.format("Last Element: %d%n", a[a.length - 1]);
    }
}
