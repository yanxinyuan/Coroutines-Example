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

import java.util.concurrent.atomic.AtomicInteger;

import com.xy.common.Helper;
import com.xy.common.Parameters;

/**
 * 
 * @author Xinyuan.Yan
 *
 */
public class ThreadMain {

	private static AtomicInteger finishTaskCount = new AtomicInteger(0);
	
	private static long startTime = 0L;
	
	private static long endTime = 0L;
	
	public static void main(String[] args) {
		try {
			if (args != null && args.length > 0) {
				Parameters.TOTAL_TASK_COUNT = Integer.valueOf(args[0]);
				if (args.length > 1) {
					Parameters.IO_WAITING_TIME = Integer.valueOf(args[1]);
					if (args.length > 2) {
						Parameters.TASK_THREAD_MAX_SIZE = Integer.valueOf(args[2]);
					}
				}
			} else {
				Helper.getInstance().man();
			} 
		} catch (Exception e) {
			System.out.println("error : illegal parameters");
			Helper.getInstance().man();
			return;
		}
		
		startMock();
		startTask();
		
		waitFinish();
		System.out.println("thread model total time : " + (endTime - startTime) + " ms");
	}

	public static void countingTask() {
		synchronized (finishTaskCount) {
			finishTaskCount.incrementAndGet();
			if (finishTaskCount.get() == Parameters.TOTAL_TASK_COUNT) {
				finishTaskCount.notify();
			}
		}
	}
	
	private static void startTask() {
		startTime = System.currentTimeMillis();
		TaskHandle.getInstance().start();
	}
	
	private static void waitFinish() {
		synchronized (finishTaskCount) {
			try {
				finishTaskCount.wait();
				endTime = System.currentTimeMillis();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void startMock() {
		for ( int i = 1; i <= Parameters.TOTAL_TASK_COUNT; i++ ) {
			TaskHandle.getInstance().addTask(new ThreadTask(String.valueOf(i)));
			}
		System.out.println("init " + Parameters.TOTAL_TASK_COUNT +" thread tasks");
	}
	
}
