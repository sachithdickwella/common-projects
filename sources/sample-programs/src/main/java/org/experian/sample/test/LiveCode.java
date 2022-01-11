package org.experian.sample.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Sachith Dickwella
 */
public class LiveCode {

    @SuppressWarnings("java:S106")
    public static void main(String[] args) {

        var alphabet = "abcdefghijklmnopqrstuvwxy12z";

        /*
         * Provide wrong output when mixed with numeric and different case (upper)
         * values since this uses inverted natural order of each character.
         */
        var reversed1 = Arrays.stream(alphabet.split(""))
                .sorted(Comparator.reverseOrder()) // Inverse natural order for sorting. Not reversing.
                .collect(Collectors.joining());
        System.out.println(reversed1);

        // -----------------------------------------------------------------------------------

        var reversed2 = new char[alphabet.length()];
        for (int i = 0; i < alphabet.length() / 2; i++) {
            var inter = alphabet.charAt(i);
            reversed2[i] = alphabet.charAt(alphabet.length() - i - 1);
            reversed2[alphabet.length() - i - 1] = inter;
        }

        System.out.println(new String(reversed2));

        // -----------------------------------------------------------------------------------

        var reversed3 = new StringBuilder(alphabet);
        System.out.println(reversed3.reverse());
        System.out.println(reversed3.insert(2, "XYZ"));

        // -----------------------------------------------------------------------------------

        StringBuilder reversed4 = new StringBuilder();
        for (var i = alphabet.length() - 1; i >= 0; i--) {
            reversed4.append(alphabet.charAt(i));
        }
        System.out.println(reversed4);

        // -------------------------Prime Numbers --------------------------------------------

        class PrimeNumbers {

            List<Integer> primeNumbers(int start, int end) {
                return IntStream.rangeClosed(start, end)
                        .filter(this::isPrime)
                        .boxed()
                        .collect(Collectors.toList());
            }

            boolean isPrime(int number) {
                for (int i = 1; i <= Math.sqrt(number); i++) {
                    if (i != 1 && number % i == 0 && i != number) {
                        return false;
                    }
                }
                return true;
            }
        }

        System.out.format("Primes: %s", new PrimeNumbers().primeNumbers(2, 100));
    }
}
