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

import java.util.concurrent.TimeUnit;

import com.xy.io.Simulator;

/**
 * 
 * @author Xinyuan.Yan
 *
 */
public class ThreadTask implements Runnable {
	
	private String id;
	
	public ThreadTask(String id) {
		this.id = id;
	}
	
	public void run() {
		io();
    	done();
	}

	// Simulate the real scenario when using i/o
	private void io() {
    	Simulator.sendAndWait(id, this);
    	synchronized (this) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
    
    private void done() {
		ThreadMain.countingTask();
    }
}
