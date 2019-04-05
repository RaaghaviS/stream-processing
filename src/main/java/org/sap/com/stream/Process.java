package org.sap.com.stream;
import java.util.HashSet;
import java.util.LinkedList;

/** 
 * Class that has methods to process incoming stream of events
**/
public class Process {
	// HashSet that stores incoming event IDs
	private HashSet<String> set = new HashSet<String>();
	
	// Queue that maintains the incoming ordering of events
	private LinkedList<Event> queue = new LinkedList<Event>();
	
	// Time until which an event remains in memory
	private int timeout = 10000;
	
	// Flag that decides whether or not to delete entries from memory
	private boolean removeOldEntries = true;
	
	public void processEvent(String eventID, String eventBody) {
		System.out.println("\nReceived event: " + eventID);
		if (!set.contains(eventID)) {
			Event event = new Event(eventID, System.currentTimeMillis());
			set.add(eventID);
			queue.push(event);
			processEventsWithoutDuplicates(eventID, eventBody);
		}
	}
	
	private void processEventsWithoutDuplicates(String eventID, String eventBody) {
		System.out.println("Processing event: "+eventID);
	}
 
	// Function that periodically checks the memory 
	// to delete events that occurred before 10 minutes

	public void monitorCache() {
		Thread monitorThread = new Thread(){
			@Override
			public void run() {
				while (removeOldEntries) {
					Event event = queue.peek();
					if (event != null) {
						long elaspedTime = System.currentTimeMillis() - event.getTimestamp();
						if (elaspedTime > timeout) {
							queue.pop();
							set.remove(event.getEventID());
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						System.out.println("Thread interrupted");
					}
				}
			}
		};
		monitorThread.start();
	}
	
	public void removeOldEntries(boolean flag) {
		removeOldEntries = flag;
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
