package com.hackerrank.atlassian;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sachith Dickwella
 */
public class BoardGame {

    private static List<Integer> clearPathIndexes(int[][] board) {
        List<Integer> indexes = new ArrayList<>();

        int rCount = 0;

        int preColumn = -1;
        int cCount = 0;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                rCount += board[x][y];
            }

            for (int z = 0; z < board.length; z++) {
                cCount += board[z][0];
            }

            if (rCount == 0) {
                indexes.add(x);
            }
            rCount = 0;

            System.out.println(cCount);
            cCount = 0;
        }

        return indexes;
    }

    @SuppressWarnings("java:S106")
    public static void main(String[] args) {
        int[][] board = {
                {0, 0, 0, 1, 1, 0},
                {0, 0, 0, 1, 0, 0},
                {0, 1, 0, 0, 0, 1},
                {0, 1, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0}
        };

        System.out.println(clearPathIndexes(board));
    }
}
