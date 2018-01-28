package org.demo.concurrent.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class TestPriorityQueue {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 无界队列
         */
        BlockingQueue<String> queue   = new PriorityBlockingQueue<String>();

        //String implements java.lang.Comparable
        queue.put("Value");
        queue.put("3");
        queue.put("2");
        queue.put("1");

//        String value = queue.take();

        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
    }
}
