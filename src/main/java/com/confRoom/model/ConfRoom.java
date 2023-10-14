package com.confRoom.model;

import java.util.HashMap;
import java.util.Map;

public class ConfRoom {
	
	private int confRoomId;
	private Boolean[] slots;
	private Map<Integer,Booking> bookings;
	int maxCapacity;
	String confRoomName;
	
	
	public ConfRoom(int maxCapacity,String confRoom) {
		this.confRoomId= (int)(Math.random()*100);
		this.slots= new Boolean [24];
		this.bookings=new HashMap<Integer,Booking>();
		this.maxCapacity=maxCapacity;
		this.confRoomName=confRoomName;
	}
	
	public int getConfRoomId() {
		return this.confRoomId;
	}

	
	public Map<Integer,Booking> getBooking(){
		return this.bookings;
	}

	public int getMaxCapacity() {
		return this.maxCapacity;
	}
	
	public Boolean[] getSlots() {
		return this.slots;
	}
	public Boolean setSlots(int[] slot) {
		for(int i=slot[0];i<=slot[1];i++) {
			if(this.slots[i]==true)
				return false;
			this.slots[i]=true;		
		}
		return true;
	}
	
	public void setBooking(Booking booking) {
		this.bookings.put(booking.getBookingId(),booking);
	}
	
	public void unsetSlots(int[] slot) {
		for(int i=slot[0];i<=slot[1];i++) {
			this.slots[i]=false;		
		}
		return;
	}
	
	public Booking unsetBooking(int Id) {
		return this.bookings.remove(Id);
	}
	
}
