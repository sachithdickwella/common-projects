package com.hackerrank.task.pn;

/**
 * @author Sachith Dickwella
 */
public class PrimeNumbers {

    public static void main(String[] args) {
        int upto = 1_000_000;
        for (int x = 1; x <= upto; x++) {
            if (isPrime(x)) {
                System.out.println(x);
            }
        }
    }

    private static boolean isPrime(int x) {
        boolean isPrime = true;
        for (int i = 1; i <= Math.sqrt(x) + 1; i++) {
            if (x % i == 0 && (i != 1 && i != x)) {
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }
}
