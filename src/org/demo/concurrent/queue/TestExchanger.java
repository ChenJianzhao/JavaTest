package org.demo.concurrent.queue;

import java.util.concurrent.Exchanger;

public class TestExchanger {

    public static void main(String[] args) throws InterruptedException {

        Exchanger exchanger = new Exchanger();

        ExchangerRunnable exchangerRunnable1 =
                new ExchangerRunnable(exchanger, "A");

        ExchangerRunnable exchangerRunnable2 =
                new ExchangerRunnable(exchanger, "B");

        ExchangerRunnable exchangerRunnable3 =
                new ExchangerRunnable(exchanger, "C");

        ExchangerRunnable exchangerRunnable4 =
                new ExchangerRunnable(exchanger, "D");

        new Thread(exchangerRunnable1).start();
        Thread.sleep(1000);
        new Thread(exchangerRunnable2).start();
        Thread.sleep(1000);
        new Thread(exchangerRunnable3).start();
        Thread.sleep(1000);
        new Thread(exchangerRunnable4).start();
        Thread.sleep(1000);

    }

    static public class ExchangerRunnable implements Runnable{

        Exchanger exchanger = null;
        Object    object    = null;

        public ExchangerRunnable(Exchanger exchanger, Object object) {
            this.exchanger = exchanger;
            this.object = object;
        }

        public void run() {
            try {
                Object previous = this.object;

                this.object = this.exchanger.exchange(this.object);

                System.out.println(
                        Thread.currentThread().getName() +
                                " exchanged " + previous + " for " + this.object
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
