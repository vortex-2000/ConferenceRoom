package com.confRoom.model;

public class Booking {
	
	private int bookingId;
	private int userId;
	private ConfRoom confRoom;
	private int[] slot;
	private int day;
	
	public Booking(int userId,ConfRoom confRoom, int day, int[]slot) {
		this.bookingId=(int)(Math.random()*1000);
		this.userId=userId;
		this.confRoom=confRoom;
		this.slot=new int[2];
		this.slot[0]=slot[0];
		this.slot[1]=slot[1];
		this.day=day;
	}
	
	public int getBookingId() {
		return this.bookingId;
	}
	
	public void setSlot(int day, int[]slot) {
		this.slot[0]=slot[0];
		this.slot[1]=slot[1];
		this.day=day;
	}
	
	public int[] getSlot() {
		return this.slot;
	}
	
	public ConfRoom getConfRoom() {
		return this.confRoom;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public int getDay() {
		return this.day;
	}
	
}
