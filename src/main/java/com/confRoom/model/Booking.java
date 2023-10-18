package com.confRoom.model;

public class Booking {
	
	private int bookingId;
	private int userId;
	private ConfRoom confRoom;
	private String[] slot;
	private String date;
	
	public Booking(int userId,ConfRoom confRoom, String date, String[]slot) {
		this.bookingId=(int)(Math.random()*1000);
		this.userId=userId;
		this.confRoom=confRoom;
		this.slot=new String[2];
		this.slot[0]=slot[0];
		this.slot[1]=slot[1];
		this.date=date;
	}
	
	public int getBookingId() {
		return this.bookingId;
	}
	
	public void setSlot(String date, String[]slot) {
		this.slot[0]=slot[0];
		this.slot[1]=slot[1];
		this.date=date;
	}
	
	public String[] getSlot() {
		return this.slot;
	}
	
	public ConfRoom getConfRoom() {
		return this.confRoom;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public String getDate() {
		return this.date;
	}
	
}
