package com.hackerrank.difficulty.hard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

/**
 * @author Sachith Dickwella
 */
@SuppressWarnings("java:S106")
public class ArrayManipulation { // NOSONAR

    public static long arrayManipulation(int n, List<List<Integer>> queries) {
        long[] out = new long[n + 1];
        long max = Long.MIN_VALUE;

        queries.forEach(row -> {
            var from = row.get(0) - 1;
            var to = row.get(1) - 1;
            var value = row.get(2);

            out[from] += value;
            out[to + 1] -= value;

            var str = Arrays.stream(out)
                    .boxed()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "[ ", " ]"));
            System.out.println(str);
        });

        for (int i = 1; i <= n; i++) {
            out[i] += out[i - 1];
            max = Math.max(out[i], max);
        }

        return max;
    }

    public static void main(String[] args) throws IOException {
        try (var reader = new BufferedReader(new FileReader("src/main/resources/array-manipulation-input.out"))) {

            long start0 = System.currentTimeMillis();

            String[] firstMultipleInput = reader.readLine()
                    .replaceAll("\\s+$", "")
                    .split("\\s");

            int n = parseInt(firstMultipleInput[0]);

            List<List<Integer>> queries = reader
                    .lines()
                    .map(line -> line.split("\\s"))
                    .map(line -> new Integer[]{parseInt(line[0]), parseInt(line[1]), parseInt(line[2])})
                    .map(Arrays::asList)
                    .toList();

            var start1 = System.currentTimeMillis();
            System.out.format("%nFile read within %dms and continue...%n%n", start1 - start0);
            long out = ArrayManipulation.arrayManipulation(n, queries);

            var end = System.currentTimeMillis();
            System.out.format("'ArrayManipulation.arrayManipulation(int, List)' executed in %dms with %d records in total %dms%n",
                    end - start1, n, end - start0);
            System.out.format("Max value: %d", out);
        }
    }
}
