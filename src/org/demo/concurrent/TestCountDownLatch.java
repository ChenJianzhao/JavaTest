package org.demo.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TestCountDownLatch {

    public static void main(String[] args) {
	
	final CountDownLatch count = new CountDownLatch(3);
	
		new Thread(new MyTask(count)).start();
		new Thread(new MyTask(count)).start();
		new Thread(new MyTask(count)).start();

		try {

			if(!count.await(1000, TimeUnit.MILLISECONDS))
				System.out.println("timeout");
			else{
				System.out.println("end");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

    }
    
    static class MyTask implements Runnable{

	CountDownLatch count;
	
	public MyTask(CountDownLatch count) {
	    this.count = count;
	}
	
		@Override
		public void run() {
			try {
				Thread.sleep(2000);

				count.countDown();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
    }
}
