package com.confRoom.model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;




public class ConfRoom {
	
	public class slotComparator implements Comparator<Slot>{
		
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		java.util.Date date1;
		java.util.Date date2;
		public int compare(Slot s1, Slot s2) {
			
			try {
				date1 =  parser.parse(s1.getSlotStartTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				date2 = parser.parse(s2.getSlotStartTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return (int) (date1.getTime() - date2.getTime());
		}
	}
	
	private int confRoomId;
	
	private Map<String, TreeSet<Slot>> bookSlot; //treeset //date
	
	
	//private Boolean[] slots; //TO DO//////////////////////////////////////////////////
	private Map<Integer,Booking> bookings;
	int maxCapacity;
	String confRoomName;
	String buildingName;
	String floorName;
	
	
	public ConfRoom(int maxCapacity,String confRoomName, String floorName, String buildingName) {
		this.confRoomId= (int)(Math.random()*100);
		this.bookSlot= new HashMap<String, TreeSet<Slot>>();
		//Arrays.fill(this.slots, false);
		this.bookings=new HashMap<Integer,Booking>();
		this.maxCapacity=maxCapacity;
		this.confRoomName=confRoomName;
		this.floorName=floorName;
		this.buildingName=buildingName;
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
	
	public Map<String, TreeSet<Slot>> getSlots() {
		//System.out.println(this.slots);
		return this.bookSlot;
	}
	public void setSlots(String date, String[] slot) {
		
		Slot slotToAdd= new Slot();
		slotToAdd.setSlotTime(slot);
		TreeSet<Slot> slotsOfDay= this.bookSlot.get(date);
		
		if(slotsOfDay==null)
			slotsOfDay= new TreeSet<Slot>(new slotComparator());

		
		slotsOfDay.add(slotToAdd);
		this.bookSlot.put(date,slotsOfDay);

	}
	
	public void setBooking(Booking booking) {
		this.bookings.put(booking.getBookingId(),booking);
	}
	
	public void unsetSlots(String date, Slot slotToRemove) {
		TreeSet<Slot> slotsOfDay= this.bookSlot.get(date);
		slotsOfDay.remove(slotToRemove);
		return;
	}
	
	public Booking unsetBooking(int Id) {
		return this.bookings.remove(Id);
	}
	
	public String getAddress() {
		return "Address:    Building Name = " + this.buildingName + ", Floor Name = " + this.floorName + ", Conference Room Name = " + this.confRoomName;
	}
	
	public String getConfRoomName() {
	 return this.confRoomName;
	}
	
}
