package org.demo.concurrent.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 计划执行线程池测试类
 * @author pc
 *
 */
public class TestScheduledExecutorService implements ITestExecute{

    @Override
    public void doExecute() {

	ScheduledExecutorService executorService = null;
	
	/**
	 * 【1】单线程计划执行器
	 */
//	executorService = Executors.newSingleThreadScheduledExecutor();
	
	/**
	 * 【2】缓存计划执行线程池
	 */
	executorService = Executors.newScheduledThreadPool(5);
	
	
	try {
	    
//	    testScheduleRunnable(executorService);
	    
//	    testScheduledCallable(executorService);
	    
//	    testScheduleAtFixedRate(executorService);
	    
	    testScheduleWithFixedDelay(executorService);
	    
	} catch (InterruptedException e) {
	    e.printStackTrace();
	} catch (ExecutionException e) {
	    e.printStackTrace();
	}finally {
//	    executorService.shutdown();   
	}
	
    }

    public void testScheduleRunnable(ScheduledExecutorService executorService) {
	
	int delaySec = 3;
	
	executorService.schedule(new Runnable() {
	    
	    @Override
	    public void run() {
		System.out.println("ScheduledExecutorService.schedule(Runnable)");
		
	    }
	}, delaySec, TimeUnit.SECONDS);
	
	for(int i=0; i<delaySec; i++) {
	    try {
		Thread.sleep(1000);
		System.out.println(i+1 + "s ...");
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }
    
    public void testScheduledCallable(ScheduledExecutorService executorService) throws InterruptedException, ExecutionException {
	
	int delaySec = 3;
	
	Future<String> result = executorService.schedule(new Callable<String>() {

	    @Override
	    public String call() throws Exception {
		return "ScheduledExecutorService.schedule(Callable<T>)";
	    }
	}, delaySec, TimeUnit.SECONDS);
	
	System.out.println(result.get());
    }
    
    public void testScheduleAtFixedRate(ScheduledExecutorService executorService) throws InterruptedException, ExecutionException {
	
	int initialDelay = 3;
	int period = 1;
	
	executorService.scheduleAtFixedRate(new Runnable() {
	    
	    @Override
	    public void run() {
		System.out.println("ScheduledExecutorService.scheduleAtFixedRate(Runnable)");
	    }
	}, initialDelay, period, TimeUnit.SECONDS);
    }
    
    public void testScheduleWithFixedDelay(ScheduledExecutorService executorService) throws InterruptedException, ExecutionException {
	
	int initialDelay = 3;
	int delay = 1;
	
	executorService.scheduleWithFixedDelay(new Runnable() {
	    
	    @Override
	    public void run() {
		try {
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		System.out.println("ScheduledExecutorService.scheduleAtFixedRate(Runnable)");
	    }
	}, initialDelay, delay, TimeUnit.SECONDS);
    }
}
