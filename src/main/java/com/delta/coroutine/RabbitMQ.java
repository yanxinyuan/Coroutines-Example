package com.delta.coroutine;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class RabbitMQ {
	
	private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	
	static {
		new Thread(new Runnable () {
			public void run() {
				try {
					while (true) {
						String message = queue.take();
						TimeUnit.SECONDS.sleep(1);
						RabbitmqGateway.getInstance().callback(message);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "Rabbitmq-callback").start();
	}
	
	public static void send(String msg) {
		try {
			queue.put(msg);
		} catch (InterruptedException e) {
		}
	}
	
}
