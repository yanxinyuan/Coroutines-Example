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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.offbynull.coroutines.user.CoroutineRunner;

/**
 * 
 * @author Xinyuan.Yan
 *
 */
public class SuspendTaskHandle {

	private static Map<String, CoroutineRunner> suspendTask = new ConcurrentHashMap<String, CoroutineRunner>();
	
	private LinkedBlockingQueue<String> notifys = new LinkedBlockingQueue<String>();  
	
	private SuspendTaskHandle() {
		for (int i = 0; i < Runtime.getRuntime().availableProcessors() ; i ++ ) {
			new Thread(new InitiateTask()).start();
		}
	}
	
	public void suspend(CoroutineRunner task) {
		String id = ((CoroutineTask)task.getCoroutine()).getId();
		if (id == null || id == "") {
			throw new RuntimeException("The id of the task is empty");
		}
		suspendTask.put(id, task);
	}
	
	public void notify(String id) {
		try {
			notifys.put(id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class InitiateTask implements Runnable {

		public void run() {
			try {
				while(true) {
					String id = notifys.take();
					CoroutineRunner runner = suspendTask.get(id);
					if (runner == null) {
						System.err.println("warn : The id : " + id + " didn't exist in suspend task but has been notified, it seems the io_waiting time is too short");
						notifys.put(id);
						continue;
					}
					// if not finish which means the method may have multiple DI
					boolean notFinish = runner.execute();
					if (notFinish) {
						return;
					} else {
						suspendTask.remove(id);
					}
				}			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class Holder {
		private static final SuspendTaskHandle instance = new SuspendTaskHandle();
	}
	
	public static SuspendTaskHandle getInstance() {
		return Holder.instance;
	}

}
