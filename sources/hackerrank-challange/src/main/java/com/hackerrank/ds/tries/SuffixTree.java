package com.hackerrank.ds.tries;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Tutorial: <a href="https://www.baeldung.com/java-pattern-matching-suffix-tree">Fast Pattern Matching of Strings Using Suffix Tree in Java</a>
 *
 * @author Sachith Dickwella
 */
public class SuffixTree {

    private static final String WORD_TERMINATION = "$";

    private static final int POSITION_UNDEFINED = -1;

    private Node root;

    private String fullText;

    public SuffixTree(String fullText) {
        this.root = new Node("", POSITION_UNDEFINED);
        this.fullText = fullText;

        var h = new HashSet<String>();
        h.clear();
    }

    private boolean addChildNode(Node parentNode, String text, int index) {
        return parentNode.children.add(new Node(text, index));
    }

    private class Node {

        private String text;

        private List<Node> children;

        private int position;

        private Node(String text, int position) {
            this.text = text;
            this.position = position;
            this.children = new LinkedList<>();
        }
    }
}
