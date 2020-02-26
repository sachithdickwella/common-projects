package com.hackerrank.task.wisetech;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Sachith Dickwella
 */
public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int productsRows = Integer.parseInt(bufferedReader.readLine().trim());
        int productsColumns = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<String>> products = new ArrayList<>();

        IntStream.range(0, productsRows).forEach(i -> {
            try {
                products.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .collect(Collectors.toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int discountsRows = Integer.parseInt(bufferedReader.readLine().trim());
        int discountsColumns = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<String>> discounts = new ArrayList<>();

        IntStream.range(0, discountsRows).forEach(i -> {
            try {
                discounts.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .collect(Collectors.toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Result.findLowestPrice(products, discounts);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

class Result {

    private enum Type {
        TYPE_1("1"), TYPE_2("2");

        private String id;

        Type(String id) {
            this.id = id;
        }
    }

    private enum Discount {
        TAG(0), TYPE(1), AMOUNT(2);

        private int index;

        Discount(int index) {
            this.index = index;
        }
    }

    private static final String EMPTY = "EMPTY";

    /*
     * Complete the 'findLowestPrice' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. 2D_STRING_ARRAY products
     *  2. 2D_STRING_ARRAY discounts
     */
    public static int findLowestPrice(List<List<String>> products, List<List<String>> discounts) {
        return products.parallelStream()
                .mapToInt(proList -> {
                    final float originalValue = Integer.parseInt(proList.get(0));
                    if (proList.size() > 1) {
                        return proList.stream()
                                .skip(1L)
                                .filter(Objects::nonNull)
                                .filter(d -> !d.equals(EMPTY))
                                .mapToInt(d -> {
                                    List<String> discount = discounts.parallelStream()
                                            .filter(dList -> dList.get(Discount.TAG.index).equals(d))
                                            .findFirst()
                                            .orElse(new ArrayList<>());

                                    float price;
                                    if (discount.get(Discount.TYPE.index).equals(Type.TYPE_1.id)) {
                                        price = originalValue * ((100f - Float.parseFloat(discount.get(Discount.AMOUNT.index))) / 100f);
                                    } else if (discount.get(Discount.TYPE.index).equals(Type.TYPE_2.id)) {
                                        price = originalValue - Float.parseFloat(discount.get(Discount.AMOUNT.index));
                                    } else {
                                        price = Float.parseFloat(discount.get(Discount.AMOUNT.index));
                                    }
                                    return Math.round(price);
                                }).boxed()
                                .min(Comparator.naturalOrder())
                                .orElse(0);
                    }
                    return Math.round(originalValue);
                }).sum();
    }
}