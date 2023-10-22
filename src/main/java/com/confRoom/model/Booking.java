package com.confRoom.model;

public class Booking {
	
	private int bookingId;
	private int userId;
	private ConfRoom confRoom;
	private Slot slot;
	private String date;
	
	public Booking(int userId,ConfRoom confRoom, String date, String[]slot) {
		this.bookingId=(int)(Math.random()*1000);
		this.userId=userId;
		this.confRoom=confRoom;
		this.slot=new Slot();
		this.slot.setSlotTime(slot);
		this.date=date;
	}
	
	public int getBookingId() {
		return this.bookingId;
	}
	
	public void setSlot(String date, String[]slot) {
		this.slot.setSlotTime(slot);
		this.date=date;
	}
	
	public Slot getSlot() {
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
