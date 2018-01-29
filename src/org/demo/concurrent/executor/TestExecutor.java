package org.demo.concurrent.executor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class TestExecutor {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
	
	ITestExecute testExecutor = null;
	
	/**
	 * 测试 ExecutorService
	 */
	testExecutor = new TestExecutorService();
	
	/**
	 * 测试 TestScheduledExecutorService
	 */
//	testExecutor = new TestScheduledExecutorService();
	
	testExecutor.doExecute();
    }
}
