package com.hackerrank.task.greedy.florist;

import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class GreedyFlorist {

    private static final String EMPTY = " ";

    private static int getMinimumCost(int k, int[] c) {
        return 0;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String[] nk = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nk[0]);
            int k = Integer.parseInt(nk[1]);

            int[] c = new int[n];

            String[] cItems = scanner.nextLine().split(EMPTY);
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                c[i] = Integer.parseInt(cItems[i]);
            }

            int minimumCost = getMinimumCost(k, c);
            System.out.println(minimumCost);
        }
    }
}
