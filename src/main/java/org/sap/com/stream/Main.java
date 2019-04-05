package org.sap.com.stream;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		Process p = new Process();
		p.removeOldEntries(true);
		
		// Remove events that occurred before 1 minute
		p.setTimeout(1000);
		
		// Periodically removes old events from memory
		p.monitorCache();
		
		// Simulating a stream of events
		p.processEvent("1", "Hello");
		p.processEvent("2", "Hello");
		p.processEvent("3", "Hello");
		
		// Does not process this event since it is a duplicate
		p.processEvent("1", "Hello");
		Thread.sleep(2000);
		
		// Processes this event now since the old event with ID "1" 
		// has been removed from memory
		p.processEvent("1", "Hello");
		p.removeOldEntries(false);
	}
}
