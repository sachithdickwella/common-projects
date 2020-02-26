package com.hackerrank.practice.day19;

import java.util.*;

/**
 * @author Sachith Dickwella
 */
public class Interfaces {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        scan.close();

        AdvancedArithmetic myCalculator = new Calculator();
        int sum = myCalculator.divisorSum(n);
        System.out.println("I implemented: " + myCalculator.getClass().getInterfaces()[0].getName() );
        System.out.println(sum);
    }
}

interface AdvancedArithmetic{
    int divisorSum(int n);
}
class Calculator implements AdvancedArithmetic {

    @Override
    public int divisorSum(int n) {
        int sum = 0;
        for (int idx = 1; idx <= n; idx++) {
            if ((n % idx) == 0) {
                sum += idx;
            }
        }
        return sum;
    }
}
