package com.coderbyte.tasks;

import java.util.regex.Pattern;

/**
 * @author Sachith Dickwella
 */
public class Main {
    public static void main(String[] args) {
        // System.out.print(arithmeticGeoII(new int[] { -32, -28, -24, -20, -16 }));
        bracketMatcher("(coder)(byte))");
    }

    public static String bracketMatcher(String str) {
        long opened = Pattern.compile("\\(").matcher(str).results().count();
        long closed = Pattern.compile("\\)").matcher(str).results().count();
        return opened ==  closed ? "1" : "0";
    }

    private static boolean isArithmetic(int[] arr) {
        int gap;
        if (arr.length > 1) gap = arr[1] - arr[0];
        else return false;

        for (int i = 1; i < arr.length; i++) {
            if (i + 1 < arr.length && (arr[i + 1] - arr[i]) != gap) return false;
        }
        return true;
    }

    private static boolean isGeometric(int[] arr) {
        float gap;
        if (arr.length > 1) gap = (float) arr[1] / arr[0];
        else return false;

        for (int i = 1; i < arr.length; i++) {
            if (i + 1 < arr.length && ((float) arr[i + 1] / arr[i]) != gap) return false;
        }
        return true;
    }

    public static String arithmeticGeoII(int[] arr) {
        if (isArithmetic(arr)) return "Arithmetic";
        else if (isGeometric(arr)) return "Geometric";
        else return "-1";
    }
}