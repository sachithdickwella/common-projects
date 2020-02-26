package com.hackerrank.practice.day15;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.LongStream;

/**
 * @author Sachith Dickwella
 */
public class LinkedList {

    public static class Node {
        int data;
        Node next;
        public Node(int d) {
            data = d;
            next = null;
        }
    }

    public static Node insert(Node head, int data) {
        if (head == null) {
            head = new Node(data);
        } else {
            if (head.next == null) {
                head.next = new Node(data);
            } else {
                head.next = insert(head.next, data);
            }
        }
        return head;
    }

    public static void display(Node head) {
        Node start = head;
        while(start != null) {
            System.out.print(start.data + " ");
            start = start.next;
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Node head = null;
            int n = sc.nextInt();

            while(n-- > 0) {
                int ele = sc.nextInt();
                head = insert(head,ele);
            }
            display(head);
        }
    }
}
