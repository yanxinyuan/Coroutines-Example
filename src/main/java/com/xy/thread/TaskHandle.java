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
package com.xy.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.xy.common.Parameters;

/**
 * 
 * @author Xinyuan.Yan
 *
 */
public class TaskHandle {

	private LinkedBlockingQueue<Runnable> tasks = new LinkedBlockingQueue<Runnable>();  
	
	private Executor executor = new ThreadPoolExecutor(0, Parameters.TASK_THREAD_MAX_SIZE,
									            60L, TimeUnit.SECONDS,
									            new LinkedBlockingQueue<Runnable>());
	
	private Boolean start = false;
	
	private TaskHandle() {
		new Thread(new InitiateTask()).start();
	}
	
	public void addTask(Runnable runner) {
		try {
			tasks.put(runner);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		synchronized (start) {
			start.notifyAll();
		}
	}
	
	class InitiateTask implements Runnable {

		public void run() {
			try {
				if (!start) {
					synchronized (start) {
						// wait notify semaphore
						start.wait();
					}
				}
				while(true) {
					Runnable runner = tasks.take();
					executor.execute(runner);
				}			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class Holder {
		private static final TaskHandle instance = new TaskHandle();
	}
	
	public static TaskHandle getInstance() {
		return Holder.instance;
	}
}
