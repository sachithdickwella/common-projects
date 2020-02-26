package com.hackerrank.ds.arrays;

import java.util.*;

/**
 * @author Sachith Dickwella (Medium)
 */
public class SparseArrays {

    private static int[] matchingStrings(String[] strings, String[] queries) {
        return Arrays.stream(queries)
                .map(q -> Arrays.stream(strings)
                        .parallel()
                        .filter(s -> Objects.equals(s, q))
                        .count())
                .map(String::valueOf)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            /*
            int stringsCount = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            String[] strings = new String[stringsCount];

            for (int i = 0; i < stringsCount; i++) {
                String stringsItem = scanner.nextLine();
                strings[i] = stringsItem;
            }

            int queriesCount = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            String[] queries = new String[queriesCount];

            for (int i = 0; i < queriesCount; i++) {
                String queriesItem = scanner.nextLine();
                queries[i] = queriesItem;
            }
            */

            String[] strings = new String[]{
                    "abcde",
                    "sdaklfj",
                    "asdjf",
                    "na",
                    "basdn",
                    "sdaklfj",
                    "asdjf",
                    "na",
                    "asdjf",
                    "na",
                    "basdn",
                    "sdaklfj",
                    "asdjf"
            };

            String[] queries = new String[]{
                    "abcde",
                    "sdaklfj",
                    "asdjf",
                    "na",
                    "basdn"
            };

            int[] res = matchingStrings(strings, queries);
            for (int re : res) {
                System.out.println(re);
            }
        }
    }
}
