package com.hackerrank.prep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Sachith Dickwella
 */
public class SalesByMatch {

    public static void main(String[] args) throws IOException {
        try (var bufferedReader = new BufferedReader(new InputStreamReader(System.in));
             var bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")))) {

            int n = Integer.parseInt(bufferedReader.readLine().trim());

            List<Integer> integers = Stream.of(bufferedReader.readLine()
                            .replaceAll("\\s+$", "")
                            .split(" "))
                    .map(Integer::parseInt).toList();

            int result = Result.sockMerchant(n, integers);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }
    }
}

class Result {

    /*
     * Complete the 'sockMerchant' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER_ARRAY ar
     */

    public static int sockMerchant(int n, List<Integer> ar) {
        // Write your code here
        return 0;
    }
}
