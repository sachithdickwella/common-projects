package com.hackerrank.practice.day21;

import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class Generics {

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Integer[] intArray = new Integer[n];
        for (int i = 0; i < n; i++) {
            intArray[i] = scanner.nextInt();
        }

        n = scanner.nextInt();
        String[] stringArray = new String[n];
        for (int i = 0; i < n; i++) {
            stringArray[i] = scanner.next();
        }

        Printer<Integer> intPrinter = new Printer<>();
        Printer<String> stringPrinter = new Printer<>();

        intPrinter.printArray(intArray);
        stringPrinter.printArray(stringArray);

        if (Printer.class.getDeclaredMethods().length > 1) {
            System.out.println("The Printer class should only have 1 method named printArray.");
        }
    }
}

class Printer<T> {

    void printArray(T[] array) {
        for (T t : array) {
            System.out.println(t);
        }
    }
}
