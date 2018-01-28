package org.demo.concurrent.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用 Semaphore 实现循环队列
 */
public class TestSemaphore {

    int size = 5;
    final AtomicInteger produceItemCount = new AtomicInteger(0);
    final AtomicInteger consumeItemCount = new AtomicInteger(0);
    final Semaphore semaphore = new Semaphore(size);
    final List<String> circularQueue ;

    public static void main(String[] args) {

        new TestSemaphore().startWork();
    }

    public TestSemaphore() {

        circularQueue  = new ArrayList<String>(size);
        for (int i = 0; i < size; i++) {
            circularQueue.add("null");
        }
    }

    public void startWork() {
        new Thread(new Producer(semaphore, circularQueue, size, produceItemCount, "producer 1")).start();
        new Thread(new Consumer(semaphore, circularQueue, size, consumeItemCount, "consumer 1")).start();
        new Thread(new Consumer(semaphore, circularQueue, size, consumeItemCount, "consumer 2")).start();
        new Thread(new Consumer(semaphore, circularQueue, size, consumeItemCount, "consumer 3")).start();
    }

    static class Producer implements Runnable{

        Semaphore semaphore;
        List<String> circularQueue;
        int size;
        AtomicInteger produceItemCount;
        String name;

        public Producer(Semaphore semaphore,List<String> circularQueue, int size, AtomicInteger produceItemCount, String name) {
            this.semaphore = semaphore;
            this.circularQueue = circularQueue;
            this.size = size;
            this.produceItemCount = produceItemCount;
            this.name = name;
        }


        @Override
        public void run() {
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void produce() throws InterruptedException {

            while (true) {

                semaphore.acquire();

                Thread.sleep(200);

                synchronized (circularQueue) {
                    int itemCount= produceItemCount.getAndIncrement();
                    String item = String.valueOf(itemCount);
                    circularQueue.set(itemCount % size, item);
                    System.out.println(name + " produce Item: " + item);
                }
            }

        }
    }

    static class Consumer implements Runnable{

        Semaphore semaphore;
        List<String> circularQueue;
        int size;
        AtomicInteger consumeItemCount;
        String name;

        public Consumer(Semaphore semaphore,List<String> circularQueue, int size, AtomicInteger consumeItemCount, String name) {
            this.semaphore = semaphore;
            this.circularQueue = circularQueue;
            this.size = size;
            this.consumeItemCount = consumeItemCount;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void consume() throws InterruptedException {

            while (true) {

                semaphore.release();

                Thread.sleep(1000);

                synchronized (circularQueue) {
                    int consumeCount = consumeItemCount.getAndIncrement();
                    String item = circularQueue.get(consumeCount % size);
                    System.out.println(name + " consume Item: " + item);
                }
            }
        }
    }
}
