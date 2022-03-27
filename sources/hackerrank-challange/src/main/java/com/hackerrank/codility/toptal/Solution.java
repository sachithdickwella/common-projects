package com.hackerrank.codility.toptal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author Sachith Dickwella
 */
public class Solution {

    public static void main(String[] args) {

        Solution solution = new Solution();
        System.out.println(solution.solution(new int[]{10, 10}));
    }

    public int solution(int[] A) {
        var factories = Arrays.stream(A)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        var goal = factories.stream()
                .parallel()
                .mapToInt(Integer::intValue)
                .sum() / 2;

        int filterCount = 0;
        for (int pollution : factories) {
            filterCount += maxByFactory(pollution);
            goal -= pollutionAfterFilterApplied(pollution, filterCount);
        }
        return filterCount;
    }

    int maxByFactory(int pollution) {
        return pollution % 2;
    }

    int pollutionAfterFilterApplied(int pollution, int filterCount) {
        int newPollution = 0;
        for (int i = 0; i < filterCount; i++) {
            pollution /= 2;
            newPollution += pollution;
        }
        return newPollution;
    }

    @SuppressWarnings("unused")
    public int solution2(int[] P, int[] S) {
        int peopleCount = Arrays.stream(P).sum();
        int seatCount = Arrays.stream(S).sum();

        int emptySeats = seatCount - peopleCount;

        int filledCars = 0;
        for (int car = 0; car < P.length; car++) {
            if (P[car] <= emptySeats) {
                filledCars++;
                emptySeats -= P[car];
            }

            if (emptySeats <= 0) break;
        }

        return P.length - filledCars;
    }

    @SuppressWarnings("unused")
    public String solution1(String message, int K) {
        final var substring = new StringJoiner(" ");

        Arrays.stream(message.split("\\s"))
                .takeWhile(w -> (substring + w).length() + 1 <= K)
                .forEach(substring::add);

        return substring.toString();
    }
}
