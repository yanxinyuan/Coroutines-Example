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

import java.util.concurrent.TimeUnit;

import com.offbynull.coroutines.user.Continuation;
import com.offbynull.coroutines.user.Coroutine;
import com.xy.io.Simulator;

/**
 * 
 * @author Xinyuan.Yan
 *
 */
final class CoroutineTask implements Coroutine {  
    
	private static final long serialVersionUID = -3344094304156260669L;

	private String id;
	
	public CoroutineTask(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
    public void run(Continuation c) {  
    	io(c);
    	done();
    } 
    
    private void io(Continuation c) {
    	Simulator.send(id);
        c.suspend(); 
    }
    
    private void done() {
		CoroutineMain.countingTask();
    }
}  
