package org.sap.com.stream;

public class Event {
	private String eventID;
	private long timestamp;
	
	public Event(String eventID, long timestamp) {
		this.eventID = eventID;
		this.timestamp = timestamp;
	}
	
	public String getEventID() {
		return eventID;
	}
	public long getTimestamp() {
		return timestamp;
	}
}
