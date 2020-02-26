package com.hackerrank.practice.day16;

import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class StringToInteger {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.next();

        try {
            int input = Integer.parseInt(s);
            System.out.println(input);
        } catch (NumberFormatException ex) {
            System.out.println("Bad String");
        }
    }
}
