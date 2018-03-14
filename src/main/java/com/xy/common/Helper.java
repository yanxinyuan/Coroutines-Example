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
package com.xy.common;


/**
 * 
 * @author Xinyuan.Yan
 *
 */
public class Helper {
	
	private Helper() {
		
	}
	
	public void man() {
		System.out.println("Usage: java -jar jarfile [args...]");
		System.out.println("       (to execute a jar file)");
		System.out.println("where args include:");
		System.out.println("         first : simulator task count, default is 1000 ");
		System.out.println("        second : io waiting time for each task, default is 10ms");
		System.out.println("         third : max thread to handle the task when using thread model, default is 200");
		System.out.println("                 coroutine model will ignore this parameter");
		System.out.println("Examples: ");
		System.out.println("          java -jar thread-model-jar-with-dependencies.jar 1000 10 200");
		System.out.println("          java -jar coroutine-model-jar-with-dependencies.jar 1000 10");
	}

	static class Holder {
		private static final Helper instance = new Helper();
	}
	
	public static Helper getInstance() {
		return Holder.instance;
	}
}
