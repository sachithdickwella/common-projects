package com.hackerrank.practice.day28;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class Regex {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int N = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        List<String> names = new ArrayList<>();
        for (int NItr = 0; NItr < N; NItr++) {
            String[] firstNameEmailID = scanner.nextLine().split(" ");

            String firstName = firstNameEmailID[0];

            String emailID = firstNameEmailID[1];

            if (emailID.endsWith("@gmail.com")) {
                names.add(firstName);
            }
        }
        scanner.close();

        names.sort(String::compareTo);
        names.forEach(System.out::println);
    }
}
