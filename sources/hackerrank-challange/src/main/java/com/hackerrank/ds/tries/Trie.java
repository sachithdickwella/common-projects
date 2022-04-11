package com.hackerrank.ds.tries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sachith Dickwella
 */
public class Trie {

    private TrieNode root;

    public void add() {

    }

    private static class TrieNode {

        private List<String> children;

        private String content;

        private boolean isEndOfWord;
    }
}
