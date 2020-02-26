package com.hackerrank.challange.alg26;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class CompareTriplets {

    private static List<Integer> compareTriplets(List<Integer> a, List<Integer> b) {
        int aScore = 0, bScore = 0;
        for (int xItr = 0; xItr < a.size(); xItr++) {
            if (a.get(xItr) > b.get(xItr)) {
                aScore++;
            } else if (b.get(xItr) > a.get(xItr)) {
                bScore++;
            }
        }

        List<Integer> out = new ArrayList<>();
        out.add(aScore);
        out.add(bScore);

        return out;
    }

    public static void main(String[] args) throws IOException {
        int t = 2;
        try (Scanner sc = new Scanner(System.in)) {

            List<List<Integer>> c = new ArrayList<>();
            while (t-- > 0) {
                String[] line = sc.nextLine().split("\\s");
                c.add(new ArrayList<>(Stream.of(line)
                        .mapToInt(Integer::parseInt)
                        .boxed()
                        .collect(Collectors.toList())));
            }

            String compared = compareTriplets(c.get(0), c.get(1))
                    .stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining(" "));

            System.out.println(compared);
        }
    }
}
