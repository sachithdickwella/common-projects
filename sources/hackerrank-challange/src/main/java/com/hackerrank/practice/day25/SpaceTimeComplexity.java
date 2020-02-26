package com.hackerrank.practice.day25;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @author Sachith Dickwella
 */
public class SpaceTimeComplexity {

    public static void main(String[] args) {
        List<Integer> input = read();

        long start = System.currentTimeMillis();
        input.forEach(in -> {
            if (isPrime(in)) {
                System.out.println("Prime");
            } else {
                System.out.println("Not prime");
            }
        });

        System.out.println("Execution time : " + (System.currentTimeMillis() - start));
    }

    private static List<Integer> read() {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        List<Integer> inputs = new ArrayList<>();
        while (t-- > 0) {
            inputs.add(sc.nextInt());
        }
        return inputs;
    }

    private static boolean isPrime(int value) {
        if (value == 1) { return false; }

        return IntStream.range(2, (int) Math.sqrt((double) value) + 1)
                .parallel()
                .filter(i -> value % i == 0)
                .noneMatch(i -> value % i == 0);
    }
}
