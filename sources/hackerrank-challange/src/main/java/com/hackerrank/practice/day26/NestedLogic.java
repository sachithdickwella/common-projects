package com.hackerrank.practice.day26;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class NestedLogic {

    private static class Date {

        int year;
        int month;
        int day;

        Date(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        @Override
        public String toString() {
            return String.format("Day: %d, Month: %d, Year: %d", day, month, year);
        }

        static int price(Date actualDate, Date dueDate) {
            if (dueDate.year == actualDate.year
                    && dueDate.month == actualDate.month && dueDate.day < actualDate.day) {
                return (actualDate.day - dueDate.day) * 15;
            } else if (dueDate.year == actualDate.year && dueDate.month < actualDate.month) {
                return (actualDate.month - dueDate.month) * 500;
            } else if (dueDate.year < actualDate.year) {
                return 10_000;
            } else {
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        List<Date> dates = read();
        System.out.println(Date.price(dates.get(0), dates.get(1)));
    }

    private static List<Date> read() {
        Scanner sc = new Scanner(System.in);

        List<Date> dates = new ArrayList<>();
        for (int count = 0; sc.hasNextInt(); count++) {
            int[] d = new int[3];
            for (int i = 0; i < d.length; i++) {
                d[i] = sc.nextInt();
            }
            dates.add(new Date(d[2], d[1], d[0]));

            if (count == 1) {
                break;
            }
        }
        return dates;
    }
}