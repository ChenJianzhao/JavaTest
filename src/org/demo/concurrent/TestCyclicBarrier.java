package org.demo.concurrent;

import java.util.Date;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by jzchen on 2017/3/19 0019.
 */
public class TestCyclicBarrier {
    public static void main(String[] args) {

        int totalThread = 5;
        final CyclicBarrier barrier = new CyclicBarrier(totalThread);

        for(int i = 0; i < totalThread; i++) {
            final String threadName = "Thread " + i;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(String.format("%s\t%s %s", new Date(), threadName, " is waiting"));
                    try {
                        barrier.await();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    System.out.println(String.format("%s\t%s %s", new Date(), threadName, "ended"));
                }
            }).start();
        }
    }
}
