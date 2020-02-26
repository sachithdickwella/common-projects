package com.hackerrank.ds.arrays;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Sachith Dickwella (Easy)
 */
public class LeftRotation {

    private static void leftRotate(int times, @NotNull final int[] array) {
        int [] newArray = new int[array.length];
        IntStream.range(0, array.length)
                .parallel()
                .forEach(x -> {
                    int index = x - times;
                    if (index < 0) {
                        newArray[array.length - Math.abs(index)] = array[x];
                    } else {
                        newArray[index] = array[x];
                    }
                });

        System.out.println(Arrays.stream(newArray)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" ")));
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String[] nd = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nd[0]);

            int d = Integer.parseInt(nd[1]);

            int[] a = new int[n];

            String[] aItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int aItem = Integer.parseInt(aItems[i]);
                a[i] = aItem;
            }

            leftRotate(d, a);
        }
    }
}
