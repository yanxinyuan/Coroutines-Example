/*
 * Copyright (c) 2018, Xinyuan.Yan, All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package com.xy.coroutine;

import java.util.concurrent.LinkedBlockingQueue;

import com.offbynull.coroutines.user.CoroutineRunner;

/**
 * 
 * @author Xinyuan.Yan
 *
 */
public class InitTaskHandle {

	private LinkedBlockingQueue<CoroutineRunner> initTask = new LinkedBlockingQueue<CoroutineRunner>();  
	
	private Boolean flag = false;
	
	private InitTaskHandle() {
		for (int i = 0; i < Runtime.getRuntime().availableProcessors() ; i ++ ) {
			new Thread(new InitiateTask()).start();
		}
	}
	
	public void addTask(CoroutineRunner runner) {
		initTask.add(runner);
	}
	
	public void start() {
		synchronized (flag) {
			flag.notifyAll();
		}
	}
	
	class InitiateTask implements Runnable {

		public void run() {
			try {
				if (!flag) {
					synchronized (flag) {
						flag.wait();
					}
				}
				while(true) {
					CoroutineRunner runner = initTask.take();
					boolean notFinish = runner.execute();
					if (notFinish) {
						SuspendTaskHandle.getInstance().suspend(runner);
					}
				}			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class Holder {
		private static final InitTaskHandle instance = new InitTaskHandle();
	}
	
	public static InitTaskHandle getInstance() {
		return Holder.instance;
	}

}
