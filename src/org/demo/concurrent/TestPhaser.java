package org.demo.concurrent;

import java.io.IOException;
import java.util.concurrent.Phaser;

/**
 * Created by jzchen on 2017/3/19 0019.
 */
public class TestPhaser {
    public static void main(String[] args) throws IOException {

        int parties = 3;
        int phases = 4;

        final Phaser phaser = new Phaser(parties) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("====== Phase : " + phase + " ======");
                return registeredParties == 0;
            }
        };

        for(int i = 0; i < parties; i++) {
            int threadId = i;

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int phase = 0; phase < phases; phase++) {
                        System.out.println(String.format("Thread %s, phase %s", threadId, phase));
                        phaser.arriveAndAwaitAdvance();
                    }
                }
            });
            thread.start();
        }
    }
}