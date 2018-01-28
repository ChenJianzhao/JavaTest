package org.demo.concurrent.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class TestArrayBlockingQueue {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 有界队列
         */
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1024);

        queue.put("1");
//        queue.put("2");

        Object object = queue.take();
        System.out.println(object);

        object = queue.poll(1000, TimeUnit.MILLISECONDS);
        System.out.println(object);


    }

}
