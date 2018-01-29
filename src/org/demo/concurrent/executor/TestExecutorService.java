package org.demo.concurrent.executor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * 线程池测试类
 * @author pc
 *
 */
public class TestExecutorService implements ITestExecute{

    @Override
    public void doExecute() {
	
	ExecutorService executorService = null;
	
	/**
	 * 【1】单线程执行器
	 */
//	executorService = Executors.newSingleThreadExecutor();
	
	/**
	 * 【2】固定大小线程池
	 */
//        executorService = Executors.newFixedThreadPool(3);
        
        /**
         * 【3】缓冲线程池
         */
//        executorService = Executors.newCachedThreadPool();
        
        /**
         * 【4】分叉合并线程池
         */
        executorService = Executors.newWorkStealingPool(); 

        try {
            
            /**
             * 测试 Executor.execute(Runnable);
             */
//	    testExecute(executorService);
	    
	    /**
	     * 测试 ExecutorService.submit(Runnable);
	     */
//	    testSubmitRunnable(executorService);
	    
	    /**
	     * 测试 ExecutorService.submit(Callable);
	     */
//	    testSubmitCallable(executorService);
	    
            /**
             * 测试 ExecutorService.invokeAny();
             */
//            testInvokeAny(executorService);
            
	    /**
	     * 测试 ExecutorService.invokeAll();
	     */
//	    testInvokeAll(executorService);
            
            /**
             * 测试 ForkJoinPool.invoke(RecursiveAction);
             * 测试 ForkJoinPool.invoke(RecursiveTask);
             */
            testWorkStealing(executorService);
	    
	} catch (InterruptedException e) {
	    e.printStackTrace();
	} catch (ExecutionException e) {
	    e.printStackTrace();
	}finally {
	    executorService.shutdown();
	}
        
    }
    
    /**
     * Executor.execute(Runnable)
     * 
     * @param executorService
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void testExecute(ExecutorService executorService) throws InterruptedException, ExecutionException {
	executorService.execute(new Runnable() {
	    
	    @Override
	    public void run() {
                System.out.println("Executor.execute(Runnable)"); 
	    }
	});
    }
    
    /**
     * ExecutorService.submit(Runnable)
     * 
     * @param executorService
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void testSubmitRunnable(ExecutorService executorService) throws InterruptedException, ExecutionException {
	
	executorService.submit(new Runnable() {
	    
	    @Override
	    public void run() {
		 System.out.println("ExecutorService.submit(Runnable)"); 
	    }
	});
    }
    
    /**
     * ExecutorService.submit(Callable<T>)
     * 
     * @param executorService
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void testSubmitCallable(ExecutorService executorService) throws InterruptedException, ExecutionException {
	
	Future<String> result = executorService.submit(new Callable<String>() {

	    @Override
	    public String call() throws Exception {
		return "ExecutorService.submit(Callable<T>)";
	    }
	});
	
	System.out.println(result.get());
	
    }
    
    /**
     * ExecutorService.invokeAny();
     * 
     * @param executorService
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void testInvokeAny(ExecutorService executorService) throws InterruptedException, ExecutionException {
	Set<Callable<String>> callables = new HashSet<Callable<String>>();
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(1000);
                return "Task 1";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(1000);
                return "Task 2";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(1000);
                return "Task 3";
            }
        });

        String result = executorService.invokeAny(callables);

        System.out.println(result);
        
    }
    
    /**
     * ExecutorService.invokeAll();
     * 
     * @param executorService
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void testInvokeAll(ExecutorService executorService) throws InterruptedException, ExecutionException {
	
	Set<Callable<String>> callables = new HashSet<Callable<String>>();
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(1000);
                return "Task 1";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(1000);
                return "Task 2";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(1000);
                return "Task 3";
            }
        });

        List<Future<String>> futures = executorService.invokeAll(callables);

        for(Future<String> future : futures){
            System.out.println("future.get = " + future.get());
        }

    }


    /**
     * ForkJoinPool.invoke()
     * 
     * @param executorService
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void testWorkStealing(ExecutorService executorService) throws InterruptedException, ExecutionException {
	
	((ForkJoinPool)executorService).invoke(new MyRecursiveAction(64));
	
    }
    
    /**
     * 不带结果的分值合并 Aciton
     * @author pc
     *
     */
    public class MyRecursiveAction extends RecursiveAction {
	  
	private long workLoad = 0;  
  	  
	    public MyRecursiveAction(long workLoad) {  
	        this.workLoad = workLoad;  
	    }  
	  
	    @Override  
	    protected void compute() {  
	  
	        //if work is above threshold, break tasks up into smaller tasks  
	        if(this.workLoad > 16) {  
	            System.out.println("Splitting workLoad : " + this.workLoad);  
	  
	            List<MyRecursiveAction> subtasks =  
	                new ArrayList<MyRecursiveAction>();  
	  
	            subtasks.addAll(createSubtasks());  
	  
	            for(RecursiveAction subtask : subtasks){  
	                subtask.fork();  
	            }  
	  
	        } else {  
	            System.out.println("Doing workLoad myself: " + this.workLoad);  
	        }  
	    }  
	  
	    private List<MyRecursiveAction> createSubtasks() {  
	        List<MyRecursiveAction> subtasks =  
	            new ArrayList<MyRecursiveAction>();  
	  
	        MyRecursiveAction subtask1 = new MyRecursiveAction(this.workLoad / 2);  
	        MyRecursiveAction subtask2 = new MyRecursiveAction(this.workLoad / 2);  
	  
	        subtasks.add(subtask1);  
	        subtasks.add(subtask2);  
	  
	        return subtasks;  
	    }
	  
	}
    
    
    /**
     * 带结果的分支合并 Task
     * @author pc
     *
     */
    public class MyRecursiveTask extends RecursiveTask<Long> {  
	  
	    private long workLoad = 0;  
	  
	    public MyRecursiveTask(long workLoad) {  
	        this.workLoad = workLoad;  
	    }  
	  
	    protected Long compute() {  
	  
	        //if work is above threshold, break tasks up into smaller tasks  
	        if(this.workLoad > 16) {  
	            System.out.println("Splitting workLoad : " + this.workLoad);  
	  
	            List<MyRecursiveTask> subtasks =  
	                new ArrayList<MyRecursiveTask>();  
	            subtasks.addAll(createSubtasks());  
	  
	            for(MyRecursiveTask subtask : subtasks){  
	                subtask.fork();  
	            }  
	  
	            long result = 0;  
	            for(MyRecursiveTask subtask : subtasks) {  
	                result += subtask.join();  
	            }  
	            return result;  
	  
	        } else {  
	            System.out.println("Doing workLoad myself: " + this.workLoad);  
	            return workLoad * 3;  
	        }  
	    }  
	      
	    private List<MyRecursiveTask> createSubtasks() {  
	        List<MyRecursiveTask> subtasks =  
	        new ArrayList<MyRecursiveTask>();  
	  
	        MyRecursiveTask subtask1 = new MyRecursiveTask(this.workLoad / 2);  
	        MyRecursiveTask subtask2 = new MyRecursiveTask(this.workLoad / 2);  
	  
	        subtasks.add(subtask1);  
	        subtasks.add(subtask2);  
	  
	        return subtasks;  
	    }  
	}  
}
