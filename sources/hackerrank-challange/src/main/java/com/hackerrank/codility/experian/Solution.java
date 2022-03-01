package com.hackerrank.codility.experian;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author Sachith Dickwella
 */
public class Solution {

    public static void main(String[] args) {
        var s = new Solution();
        System.out.println(s.solution("Forget  CVs..Save time . x x"));
    }

    public int solution(String S) {
        return Arrays.stream(S.split("[.?!]"))
                .map(String::trim)
                .mapToInt(sentence -> sentence.split("\\s+").length)
                .max().orElse(0);

    }

    public int solution(int A, int B) {
        double sqrtA = (A > 0 ? Math.sqrt(A) : Math.sqrt(A * -1D));
        double sqrtB = Math.sqrt(B);

        if (sqrtA > sqrtB) {
            sqrtA = sqrtA + sqrtB;
            sqrtB = sqrtA - sqrtB;
            sqrtA = sqrtA - sqrtB;
        }

        if (sqrtA != (int) sqrtA) sqrtA = Math.ceil(sqrtA);

        return (int) IntStream.rangeClosed((int) sqrtA, (int) sqrtB).count();
     }
}
