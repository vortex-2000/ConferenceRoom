package com.comfRoom.model;

public class Booking {
	
	private int bookingId;
	private int userId;
	private ConfRoom confRoom;
	private int[]slot;
	
	public Booking(int userId,ConfRoom confRoom) {
		this.bookingId=(int)Math.random()*1000;
		this.userId=userId;
		this.confRoom=confRoom;
	}
	
	public int getBookingId() {
		return this.bookingId;
	}
	
	public void setSlot(int[]slot) {
		this.slot=slot;
	}
	
	public int[] getSlot() {
		return this.slot;
	}
	
	public ConfRoom getConfRoom() {
		return this.confRoom;
	}
	
}
