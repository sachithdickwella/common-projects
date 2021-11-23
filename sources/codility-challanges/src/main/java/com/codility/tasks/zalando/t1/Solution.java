package com.codility.tasks.zalando.t1;

import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.String.valueOf;

/**
 * @author Sachith Dickwella
 * @version 1.0.0
 */
@SuppressWarnings({"java:S117", "java:S106"})
public class Solution {

    public static void main(String[] args) {
        Solution s1 = new Solution();

        System.out.println(s1.solution("00000"));
    }


    public int solution(String S) {
        var split = S.split("");

        return IntStream.range(0, split.length)
                .map(idx -> {
                    if (idx + 1 < split.length) {
                        var number = format("%s%s", split[idx], split[idx + 1]);

                        if (number.charAt(0) == '0') {
                            if (number.charAt(1) == '0') {
                                return 0;
                            } else {
                                return parseInt(valueOf(number.charAt(1)));
                            }
                        } else {
                            return parseInt(number);
                        }
                    } else {
                        return 0;
                    }
                }).max()
                .orElse(0);
    }
}
