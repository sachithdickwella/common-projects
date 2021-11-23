package com.codility.tasks.zalando.t3;

import java.util.HashSet;

/**
 * @author Sachith Dickwella
 * @version 1.0.0
 */
@SuppressWarnings({"java:S117", "java:S106"})
public class Solution {

    public static void main(String[] args) {
        Solution s3 = new Solution();
        System.out.println(s3.solution(new int[]{5, 2, 4, 6, 3, 7}));
    }

    public int solution(int[] A) {
        var costs = new HashSet<Integer>();

        for (int x = 1; x < A.length - 1; x++) {
            for (int y = x + 2; y < A.length - 1; y++) {
                costs.add(A[x] + A[y]);
            }
        }

        return costs.parallelStream()
                .min(Integer::compareTo)
                .orElse(0);
    }
}
