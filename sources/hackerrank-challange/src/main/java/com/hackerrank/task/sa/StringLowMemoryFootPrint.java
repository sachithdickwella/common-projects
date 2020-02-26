package com.hackerrank.task.sa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Small heap execution program for testing. Therefore the heap size limited until
 *
 * @author Sachith Dickwella
 */
public class StringLowMemoryFootPrint {

    /**
     * Length of the generated random string.
     */
    private static final int STRING_LENGTH = 100;
    /**
     * {@link Random} instance which used to generate random {@link String}.
     */
    private static final Random RANDOM_GENERATOR = new Random();
    /**
     * Left start position (65 = A ASCII value).
     */
    private static final int LEFT_LIMIT = 65;
    /**
     * Right end position (122 = z ASCII value).
     */
    private static final int RIGHT_LIMIT = 122;
    /**
     * Output file.
     */
    private static final String FILE_NAME = "output.txt";
    /**
     * Buffer size for the {@link BufferedWriter}.
     */
    private static final int BUFFER_SIZE_10MB = 1024 * 1024;

    public static void main(String[] args) throws Exception {
        write((int) Math.pow(2.0D, 30.0D) - 1);
    }

    private static void write(int x) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME), BUFFER_SIZE_10MB)) {
            for (int i = 0; i < x; i++) {
                String randomString = RANDOM_GENERATOR.ints(LEFT_LIMIT, RIGHT_LIMIT)
                        .limit(STRING_LENGTH)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
                writer.write(randomString, 0, randomString.length());
                writer.newLine();
            }
        }
    }
}
