package com.confRoom.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;



public class ConfRoom {
	
	private int confRoomId;
	
	private Map<Integer, TreeSet<Slot>> bookSlot; //treeset //date
	
	
	//private Boolean[] slots; //TO DO//////////////////////////////////////////////////
	private Map<Integer,Booking> bookings;
	int maxCapacity;
	String confRoomName;
	
	
	public ConfRoom(int maxCapacity,String confRoomName) {
		this.confRoomId= (int)(Math.random()*100);
		this.bookSlot= new HashMap<Integer, TreeSet<Slot>>();
		//Arrays.fill(this.slots, false);
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
	
	public Map<Integer, TreeSet<Slot>> getSlots() {
		//System.out.println(this.slots);
		return this.bookSlot;
	}
	public void setSlots(int day, int[] slot) {
		
		Slot slotToAdd= new Slot();
		slotToAdd.setSlotTime(slot);
		TreeSet<Slot> slotsOfDay= this.bookSlot.get(day);
		if(slotsOfDay==null)
			slotsOfDay= new TreeSet<Slot>();
		
		slotsOfDay.add(slotToAdd);
		this.bookSlot.put(day,slotsOfDay);

	}
	
	public void setBooking(Booking booking) {
		this.bookings.put(booking.getBookingId(),booking);
	}
	
	public void unsetSlots(int day, Slot slotToRemove) {
		TreeSet<Slot> slotsOfDay= this.bookSlot.get(day);
		slotsOfDay.remove(slotToRemove);
		return;
	}
	
	public Booking unsetBooking(int Id) {
		return this.bookings.remove(Id);
	}
	
}
