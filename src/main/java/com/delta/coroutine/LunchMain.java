package com.delta.coroutine;

import java.util.concurrent.TimeUnit;

import com.offbynull.coroutines.user.CoroutineRunner;

public class LunchMain {
	
	public static void main(String[] args) {
		startMock();
	}

	public static void startMock() {
		new Thread(new Runnable () {
			public void run() {
				try {
					for (int i = 1; i <= 5; i ++) {
						InitiateHandle.getInstance().addTask(new CoroutineRunner(new SpecTask(String.valueOf(i))));
						System.out.println("initiate " + i +" tasks");
						TimeUnit.SECONDS.sleep(2);
					}
				} catch (InterruptedException e) {
				}
			}
		}).start();
	}
	
	
}
