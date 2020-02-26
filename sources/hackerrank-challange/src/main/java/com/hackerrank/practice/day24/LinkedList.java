package com.hackerrank.practice.day24;

import java.util.Scanner;

/**
 * @author Sachith Dickwella
 */
public class LinkedList {

    public static Node removeDuplicates(Node head) {
        Node node = null;
        while (head != null) {
            if (!contains(node, head.data)) {
                node = insert(node, head.data);
            }
            head = head.next;
        }
        return node;
    }

    private static boolean contains(Node head, int data) {
        Node start = head;
        while (start != null) {
            if (start.data == data) {
                return true;
            }
            start = start.next;
        }
        return false;
    }

    public static Node insert(Node head, int data) {
        Node p = new Node(data);
        if (head == null)
            head = p;
        else if (head.next == null)
            head.next = p;
        else {
            Node start = head;
            while (start.next != null)
                start = start.next;
            start.next = p;

        }
        return head;
    }

    public static void display(Node head) {
        Node start = head;
        while (start != null) {
            System.out.print(start.data + " ");
            start = start.next;
        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Node head = null;
        int T = sc.nextInt();
        while (T-- > 0) {
            int ele = sc.nextInt();
            head = insert(head, ele);
        }
        head = removeDuplicates(head);
        display(head);

    }
}

class Node {

    int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }
}