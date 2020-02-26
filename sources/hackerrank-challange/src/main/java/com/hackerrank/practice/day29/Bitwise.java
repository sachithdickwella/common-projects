package com.hackerrank.practice.day29;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class Bitwise {


    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            int t = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            List<Integer> out = new ArrayList<>();
            for (int tItr = 0; tItr < t; tItr++) {
                String[] nk = scanner.nextLine().split(" ");

                int n = Integer.parseInt(nk[0]);
                int k = Integer.parseInt(nk[1]);

                int maxUptoK = 0;
                for (int nItr = 1; nItr <= n; nItr++) {
                    for (int nsItr = nItr + 1; nsItr <= n; nsItr++) {
                        int add = nItr & nsItr;
                        if (add > maxUptoK && add < k) {
                            maxUptoK = add;
                        }
                    }
                }
                out.add(maxUptoK);
            }
            out.forEach(System.out::println);
        }
    }
}
