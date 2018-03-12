package com.delta.coroutine;

public class RabbitmqGateway {
	
	public void callback(String message) {
		SuspendHandle.getInstance().notify(message);
	}

	static class Holder {
		private static final RabbitmqGateway instance = new RabbitmqGateway();
	}
	
	public static RabbitmqGateway getInstance() {
		return Holder.instance;
	}
	
}
