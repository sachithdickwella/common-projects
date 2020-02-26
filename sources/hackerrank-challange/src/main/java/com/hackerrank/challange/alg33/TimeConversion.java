package com.hackerrank.challange.alg33;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class TimeConversion {

    private static String timeConversion(String s) throws ParseException {
        SimpleDateFormat sdfi = new SimpleDateFormat("hh:mm:ssa");
        SimpleDateFormat sdfo = new SimpleDateFormat("HH:mm:ss");
        return sdfo.format(sdfi.parse(s));
    }

    public static void main(String[] args) throws ParseException {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(timeConversion(scanner.nextLine()));
        }
    }
}
