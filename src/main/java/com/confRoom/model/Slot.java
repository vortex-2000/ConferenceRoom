package com.confRoom.model;

public class Slot {
	private  String startTime;
	private String endTime;
	
	public String getSlotStartTime(){
		return this.startTime;
	}
	
	public String getSlotEndTime(){
		return this.endTime;
	}
	
	public void setSlotStartTime(String start) {
		this.startTime=start;
	}
	
	public void setSlotEndTime(String end) {
		this.endTime=end;
	}
	
}
