package org.demo.concurrent.queue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class TestLinkedBlockDeque {

    public static void main(String[] args) throws InterruptedException {


        BlockingDeque<String> deque = new LinkedBlockingDeque<String>();

        deque.addFirst("1");
        deque.addLast("2");
        deque.add("3");

        String two = deque.takeLast();
        String one = deque.takeFirst();

        System.out.println(one);
        System.out.println(two);
    }
}
