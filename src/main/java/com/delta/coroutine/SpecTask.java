package com.delta.coroutine;

import com.offbynull.coroutines.user.Continuation;
import com.offbynull.coroutines.user.Coroutine;

final class SpecTask implements Coroutine {  
    
	private static final long serialVersionUID = -3344094304156260669L;

	private String id;
	
	public SpecTask(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
    public void run(Continuation c) {  
    	System.out.println(id + " start doing something");
    	
    	doing(c);
    	
        if (isFinish()) {
        	System.out.println(id + " recover and finish");
        } else {
        	doingAgain(c);
        	System.out.println(id + " recover and finish");
        }
    } 
    
    private void doing(Continuation c) {
    	System.out.println(id + " is doing now");
    	RabbitMQ.send(id);

    	System.out.println(id + " start suspend");
        c.suspend(); 
    }
    
    private void doingAgain(Continuation c) {
    	System.out.println(id + " is doing again");
    	RabbitMQ.send(id);

    	System.out.println(id + " start suspend again");
        c.suspend(); 
    }
    
    
    private boolean isFinish() {
    	return Integer.valueOf(id) % 2 != 0;
    }
}  
