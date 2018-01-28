package org.demo.concurrent.queue;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TestDelayQueue {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayedElement> queue = new DelayQueue();


        DelayedElement element1 = new DelayedElement(3000, "delay 3s element");
        queue.put(element1);
        DelayedElement element2 = new DelayedElement(2000, "delay 2s element");
        queue.put(element2);
        DelayedElement element3 = new DelayedElement(1000, "delay 1s element");
        queue.put(element3);

        for(int i=0; i<queue.size()+1; i++ ) {

            System.out.println("start take: " + new Date(System.currentTimeMillis()));

            DelayedElement e = (DelayedElement)queue.take();

            System.out.println("take element: " + e.getName() + new Date(System.currentTimeMillis()) + "\n");

        }
    }

    static class DelayedElement implements Delayed{


        long delay;
        long expire;
        String elementName;

        public DelayedElement(long delay, String elementName) {
            this.delay = delay;
            this.expire = System.currentTimeMillis() + delay;
            this.elementName = elementName;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return expire - System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {
            int value =  (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
//            System.out.println(value);
            return value;
        }


        public String getName() {
            return elementName;
        }

        public long getExpire() {
            return expire;
        }

        @Override
        public String toString() {
            return getName();
        }
    }
}
