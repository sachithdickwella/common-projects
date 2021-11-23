package com.codility.tasks.zalando.t2;

/**
 * @author Sachith Dickwella
 * @version 1.0.0
 */
@SuppressWarnings({"java:S117", "java:S106"})
public class Solution {

    public static void main(String[] args) {
        Solution s2 = new Solution();
        System.out.println(s2.solution("aabbcc", new int[]{1, 2, 1, 2, 1, 2}));
    }

    public int solution(String S, int[] C) {
        int count = 0;

        for (int x = 0; x <= S.length(); x++) {
            if (x + 1 < S.length() && (S.charAt(x) == S.charAt(x + 1))) {
                count += Math.min(C[x], C[x + 1]);
            }
        }

        return count;
    }
}
