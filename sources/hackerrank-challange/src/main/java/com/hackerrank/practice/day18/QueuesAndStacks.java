package com.hackerrank.practice.day18;

import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Sachith Dickwella
 */
public class QueuesAndStacks {

    private Stack<Character> stack;

    private Queue<Character> queue;

    private QueuesAndStacks () {
        this.stack = new Stack<>();
        this.queue = new LinkedBlockingQueue<>();
    }

    void pushCharacter(Character c) {
        this.stack.push(c);
    }

    void enqueueCharacter(Character c) {
        this.queue.offer(c);
    }

    Character popCharacter() {
        return this.stack.pop();
    }

    Character dequeueCharacter() {
        return this.queue.remove();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        scan.close();

        // Convert input String to an array of characters:
        char[] s = input.toCharArray();

        // Create a Solution object:
        QueuesAndStacks p = new QueuesAndStacks();

        // Enqueue/Push all chars to their respective data structures:
        for (char c : s) {
            p.pushCharacter(c);
            p.enqueueCharacter(c);
        }

        // Pop/Dequeue the chars at the head of both data structures and compare them:
        boolean isPalindrome = true;
        for (int i = 0; i < s.length/2; i++) {
            if (p.popCharacter() != p.dequeueCharacter()) {
                isPalindrome = false;
                break;
            }
        }

        //Finally, print whether string s is palindrome or not.
        System.out.println( "The word, " + input + ", is "
                + ( (!isPalindrome) ? "not a palindrome." : "a palindrome." ) );
    }
}
