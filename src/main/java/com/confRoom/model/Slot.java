package com.confRoom.model;

public class Slot {
	private  int startTime;
	private int endTime;
	
	public int getSlotStartTime(){
		return this.startTime;
	}
	
	public int getSlotEndTime(){
		return this.endTime;
	}
	
	public void setSlotTime(int[] slot) {
		this.startTime=slot[0];
		this.endTime=slot[0];
	}
	
}
