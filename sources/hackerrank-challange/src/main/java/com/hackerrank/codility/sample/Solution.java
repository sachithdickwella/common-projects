package com.hackerrank.codility.sample;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.Map.Entry;
import static java.util.stream.Collectors.toMap;

/**
 * @author Sachith Dickwella
 */
@SuppressWarnings({"java:S106", "java:S117"})
public class Solution {

    public static void main(String[] args) {
        var sol = new Solution();

        var A = new int[]{6, 1, 4, 6, 3, 2, 7, 6};
        var max = sol.solution(A, 5, 3);

        System.out.println(max);
    }

    public int solution(int[] A, int K, int L) {
        if (A.length < K + L) return -1;

        var probabilityK = sumValues(A, K);
        var probabilityL = sumValues(A, L);

        var idealK = idealK(probabilityK, A, K, L);
        var idealL = idealL(probabilityL, idealK, K, L);

        System.out.println(idealK);
        System.out.println(idealL);

        return idealK.getValue() + idealL.getValue();
    }

    private Entry<Integer, Integer> idealK(Map<Integer, Integer> probabilities, int[] A, int K, int L) {
        return probabilities.entrySet()
                .stream()
                .max(Entry.comparingByValue())
                .map(max -> {
                    if (max.getKey() - L >= 0 || max.getKey() + K - 1 + L - 1 < A.length) {
                        return max;
                    } else {
                        System.out.println(probabilities);
                        probabilities.remove(max.getKey(), max.getValue());
                        return idealK(probabilities, A, K, L);
                    }
                })
                .orElse(new SimpleEntry<>(0, 0));
    }

    private Entry<Integer, Integer> idealL(Map<Integer, Integer> probabilities,
                                           Entry<Integer, Integer> idealK,
                                           int K,
                                           int L) {
        return probabilities.entrySet()
                .stream()
                .max(Entry.comparingByValue())
                .map(max -> {
                   if ((max.getKey() >= idealK.getKey() && max.getKey() <= idealK.getKey() + K - 1)
                           || (max.getKey() + L - 1 >= idealK.getKey() && max.getKey() + L - 1 <= idealK.getKey() + K -1)) {
                       probabilities.remove(max.getKey(), max.getValue());
                       return idealL(probabilities, idealK, K, L);
                   } else {
                       return max;
                   }
                })
                .orElse(new SimpleEntry<>(0, 0));
    }

    private Map<Integer, Integer> sumValues(int[] original, int length) {
        return IntStream.range(0, original.length)
                .mapToObj(idx -> {
                    if (idx <= original.length - length) {
                        return new SimpleEntry<>(
                                idx,
                                Arrays.stream(Arrays.copyOfRange(original, idx, idx + length)).sum());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }
}