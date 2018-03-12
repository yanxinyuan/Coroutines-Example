package com.delta.coroutine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.offbynull.coroutines.user.CoroutineRunner;

public class SuspendHandle {

	private static Map<String, CoroutineRunner> suspendTask = new ConcurrentHashMap<String, CoroutineRunner>();
	
	private SuspendHandle() {
		
	}
	
	public void suspend(CoroutineRunner task) {
		String id = ((SpecTask)task.getCoroutine()).getId();
		if (id == null || id == "") {
			throw new RuntimeException("The id of the task is empty");
		}
		suspendTask.put(id, task);
	}
	
	public void notify(String id) {
		CoroutineRunner runner = suspendTask.get(id);
		if (runner == null) {
			throw new RuntimeException("Do not found this task id : " + id);
		}
		// if not finish which means the method may have multiple DI
		boolean notFinish = runner.execute();
		if (notFinish) {
			return;
		} else {
			suspendTask.remove(id);
		}
	}
	
	static class Holder {
		private static final SuspendHandle instance = new SuspendHandle();
	}
	
	public static SuspendHandle getInstance() {
		return Holder.instance;
	}

}
