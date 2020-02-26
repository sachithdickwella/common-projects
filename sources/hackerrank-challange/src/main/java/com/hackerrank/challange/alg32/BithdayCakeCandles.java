package com.hackerrank.challange.alg32;

import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @author Sachith Dickwella
 */
public class BithdayCakeCandles {

    private static int birthdayCakeCandles(int[] ar) {
        int max = IntStream.of(ar).max().getAsInt();
        return (int) IntStream.of(ar).filter(i -> i == max).count();
    }

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            int arCount = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] ar = new int[arCount];

            String[] arItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < arCount; i++) {
                int arItem = Integer.parseInt(arItems[i]);
                ar[i] = arItem;
            }

            int result = birthdayCakeCandles(ar);
            System.out.println(result);
        }
    }
}
