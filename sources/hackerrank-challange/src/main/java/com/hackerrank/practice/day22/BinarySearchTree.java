package com.hackerrank.practice.day22;

import java.util.Scanner;
import java.util.Stack;

/**
 * @author Sachith Dickwella
 */
public class BinarySearchTree {

    public static Node insert(Node root, int data) {
        if (root == null) {
            return new Node(data);
        } else {
            Node cur;
            if (data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        Node root = null;
        while (T-- > 0) {
            int data = sc.nextInt();
            root = insert(root, data);
        }
        // int height = getHeight(root);
        levelOrder(root);
    }

    @Deprecated
    public static int getHeight(Node root) {
        int depth = 0;
        if (root == null) {
            return depth;
        }
        return traverse(root);
    }

    @Deprecated
    private static int traverse(Node node) {
        int left = 0, right = 0, max = 0;
        if (node.left != null) {
            left++;
            left += traverse(node.left);
            if (max < left) {
                max = left;
            }
        }

        if (node.right != null) {
            right++;
            right += traverse(node.right);
            if (max < right) {
                max = right;
            }
        }
        return max;
    }

    /**
     * Depth first tree.
     */
    private static void levelOrder(Node root) {
        if (root != null) {
            Stack<Node> stack = new Stack<>();
            stack.push(root);

            while(!stack.isEmpty()) {
                Node node = stack.pop();
                System.out.format("%d ", node.data);

                if (node.right != null) {
                    stack.push(node.right);
                }

                if (node.left != null) {
                    stack.push(node.left);
                }
            }
        }
    }
}

class Node {
    Node left, right;
    int data;

    Node(int data) {
        this.data = data;
        left = right = null;
    }
}
