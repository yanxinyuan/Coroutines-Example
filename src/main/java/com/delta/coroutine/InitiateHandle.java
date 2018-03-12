package com.delta.coroutine;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.offbynull.coroutines.user.CoroutineRunner;

public class InitiateHandle {

	private LinkedBlockingQueue<CoroutineRunner> initiateTask = new LinkedBlockingQueue<CoroutineRunner>();  
	
	private InitiateHandle() {
		new Thread(new InitiateTask(), "InitiateTask").start();
	}
	
	public void addTask(CoroutineRunner runner) {
		initiateTask.add(runner);
	}
	
	class InitiateTask implements Runnable {

		public void run() {
			try {
				TimeUnit.SECONDS.sleep(6);
				while(true) {
					CoroutineRunner runner = initiateTask.take();
					boolean notFinish = runner.execute();
					if (notFinish) {
						SuspendHandle.getInstance().suspend(runner);
					}
				}			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class Holder {
		private static final InitiateHandle instance = new InitiateHandle();
	}
	
	public static InitiateHandle getInstance() {
		return Holder.instance;
	}

}
