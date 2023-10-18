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
	
	public void setSlotTime(String[] slot) {
		this.startTime=slot[0];
		this.endTime=slot[1];
	}
	
}
