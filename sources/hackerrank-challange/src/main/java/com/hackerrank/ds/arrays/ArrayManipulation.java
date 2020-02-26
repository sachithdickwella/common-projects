package com.hackerrank.ds.arrays;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author Sachith Dickwella
 */
public class ArrayManipulation {

    private static long arrayManipulation(int n, @NotNull List<long[]> queries) {
        long s1 = System.nanoTime();
        final long[] results = new long[n];

        System.out.println(ratio(n, 1));

        System.out.println(n / 256);
        System.out.println(n % 256);

        long s2 = System.nanoTime();
        System.out.format("%d ns%n", s2 - s1);

        queries.forEach(a -> {
            int length = (int) (a[1] - a[0]);
            int ratio = ratio(length, 1);
            int remainder = length % ratio;

            for (int i = (int) a[0]; i < (length / ratio) + a[0]; i++) {
                results[i] += a[2];
                for (int x = i * ratio; x < (i + 1) * ratio; x++) {
                    results[x] += a[2];
                }
            }

            for (int i = length - remainder; i < length; i++) {
                results[i] += a[2];
            }
        });

        long s3 = System.nanoTime();
        System.out.format("%d ns%n", s3 - s2);

        long max = Arrays.stream(results)
                .max()
                .orElse(0);
        System.out.format("%d ns%n", System.nanoTime() - s3);

        return max;
    }

    /**
     * Calculate the ratio value that can be used to micro update the array on single
     * iteration.
     *
     * @param l  Length of the array.
     * @param ir Initial ration to begin with.
     * @return the ratio that be used to update the original array.
     */
    private static int ratio(int l, int ir) {
        if (l % (ir * 2) != 0) {
            return ir * 2;
        } else {
            return ratio(l, ir * 2);
        }
    }

    public static void main(String[] args) throws IOException {
        /*List<long[]> queries = List.of(
                new long[]{1, 2, 100},
                new long[]{2, 5, 100},
                new long[]{3, 4, 100}
        );

        int[] results = new int[10];

        queries.forEach(a -> {
            int length = (int) (a[1] - a[0]);
            int ratio = ratio(length, 1);
            int remainder = length % ratio;

            for (int i = (int) a[0]; i < (length / ratio) + a[0]; i++) {
                results[i] += a[2];
                for (int x = i * ratio; x < (i + 1) * ratio; x++) {
                    results[x] += a[2];
                }
            }

            for (int i = length - remainder; i < length; i++) {
                results[i] += a[2];
            }
        });

        System.out.println(Arrays.stream(results).max().orElse(0));*/

        try (Scanner scanner = new Scanner(System.in)) {
            /*String[] nm = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nm[0]);
            int m = Integer.parseInt(nm[1]);

            int[][] queries = new int[m][3];

            for (int i = 0; i < m; i++) {
                String[] queriesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 3; j++) {
                    int queriesItem = Integer.parseInt(queriesRowItems[j]);
                    queries[i][j] = queriesItem;
                }
            }*/

            // First test data.
            /*List<long[]> queries = List.of(
                    new long[]{1, 2, 100},
                    new long[]{2, 5, 100},
                    new long[]{3, 4, 100}
            );

            long result = arrayManipulation(10, queries); // n = 4 for input file.
            System.out.println(result);*/

            // Second test data.
            /*List<long[]> queries = List.of(
                    new long[]{2, 6, 8},
                    new long[]{3, 5, 7},
                    new long[]{1, 8, 1},
                    new long[]{5, 9, 15}
            );

            long result = arrayManipulation(10, queries); // n = 4 for input file.
            System.out.println(result); // expected -> 7496167325*/

            // third test data.
            try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/array-manipulation-input-large.txt"))) {
                List<long[]> ints = reader.lines().parallel().skip(1).map(line -> {
                    String[] values = line.split("\\s");
                    return new long[]{
                            Long.parseLong(values[0]),
                            Long.parseLong(values[1]),
                            Long.parseLong(values[2])
                    };
                }).collect(Collectors.toList());

                long result = arrayManipulation(10_000_000, ints); // n = 100000 for input file.
                System.out.println(result); // expected -> 2510535321
            }
        }
    }
}
