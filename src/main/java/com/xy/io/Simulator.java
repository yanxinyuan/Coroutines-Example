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
package com.xy.io;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.xy.common.Parameters;
import com.xy.coroutine.SuspendTaskHandle;

/**
 * 
 * @author Xinyuan.Yan
 *
 */
public class Simulator {
	
	private static LinkedBlockingQueue<Pairs<String, Object>> queue = new LinkedBlockingQueue<Pairs<String, Object>>();
	
	static {
		new Thread(new Client()).start();
	}
	
	static class Client implements Runnable {

		public void run() {
			try {
				while (true) {
					Pairs<String, Object> entry = queue.take();
					TimeUnit.MILLISECONDS.sleep(Parameters.IO_WAITING_TIME);
					ionotify(entry.getK(), entry.getV());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void send(String msg) {
		sendAndWait(msg, null);
	}
	
	public static void sendAndWait(String msg, Object obj) {
		try {
			queue.put(new Pairs<String, Object>(msg, obj));
		} catch (InterruptedException e) {

		}
	}
	
	private static void ionotify(String message, Object obj) {
		if (obj == null) {
			SuspendTaskHandle.getInstance().notify(message);
		} else {
			synchronized (obj) {
				obj.notify();
			}
		}
	}
}
