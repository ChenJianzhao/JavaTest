package org.demo.concurrent.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class TestSynchronousQueue {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> queue = new SynchronousQueue<String>();

        new Thread(new Comsumer(queue)).start();

        queue.put("1");
        System.out.println("put");

        queue.put("1");
        System.out.println("put");



    }

    static class Comsumer implements Runnable {

        BlockingQueue<String> queue;

        public Comsumer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        public void comsume() throws InterruptedException {
            while(true) {
                Thread.sleep(1000);
                System.out.println("take -->");
                System.out.println(queue.poll(1000, TimeUnit.MILLISECONDS));
            }
        }

        @Override
        public void run() {
            try {
                comsume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
