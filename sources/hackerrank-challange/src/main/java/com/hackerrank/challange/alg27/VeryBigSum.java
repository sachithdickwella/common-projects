package com.hackerrank.challange.alg27;

import java.util.Scanner;
import java.util.stream.LongStream;

/**
 * @author Sachith Dickwella
 */
public class VeryBigSum {

    private static long aVeryBigSum(long[] ar) {
        return LongStream.of(ar).sum();
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int arCount = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            long[] ar = new long[arCount];

            String[] arItems = scanner.nextLine().split("\\s");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < arCount; i++) {
                long arItem = Long.parseLong(arItems[i]);
                ar[i] = arItem;
            }

            System.out.println(aVeryBigSum(ar));
        }
    }
}
