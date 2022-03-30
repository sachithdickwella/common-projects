package com.hackerrank.google;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sachith Dickwella
 */
public class FindMatchingSumFromArrayElements {

    private static List<int[]> getElementsEqualsToSum(int[] ints, int sum) {
        var out = new LinkedList<int[]>();
        var intSet = new HashSet<>();
        for (int anInt : ints) {
            int remainder = sum - anInt;

            if (intSet.contains(anInt)) {
                var pair = new int[] {remainder, sum - remainder};
                out.add(pair);
            } else {
                intSet.add(remainder);
            }
        }

        return out;
    }

    public static void main(String[] args) {
        var out = getElementsEqualsToSum(new int[]{1, 2, 4, 4, 6}, 8)
                .stream()
                .map(array -> Arrays.stream(array)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", ", "[", "]")))
                .collect(Collectors.joining(", ", "[", "]"));

        System.out.println(out); // NOSONAR
    }
}
