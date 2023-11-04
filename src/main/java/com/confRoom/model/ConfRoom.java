package com.confRoom.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	
	private Map<String, TreeSet<Slot>> bookSlot; //tree=sorted
	int maxCapacity;
	String confRoomName; //private
	int buildingId;
	int floorId;
	
	
	public ConfRoom(int maxCapacity,String confRoomName, int floorId, int buildingId) {
		this.confRoomId= (int)(Math.random()*100);
		this.bookSlot= new HashMap<String, TreeSet<Slot>>();
		this.maxCapacity=maxCapacity;
		this.confRoomName=confRoomName;
		this.floorId=floorId;
		this.buildingId=buildingId;
	}
	
	public int getConfRoomId() {
		return this.confRoomId;
	}

	
	public Map<String,TreeSet<Slot>> getSlots(){
		return this.bookSlot;
	}
	
	public int getMaxCapacity() {
		return this.maxCapacity;
	}
	
	
	public void setBooking(Booking booking) {     //****** dont store booking obj in confRoom
		
		String date=booking.getDate();
		TreeSet<Slot> slotsOfDay= this.bookSlot.get(date);
		
		if(slotsOfDay==null)
			slotsOfDay= new TreeSet<Slot>(new slotComparator());
		
		slotsOfDay.add(booking.getSlot());
		
		this.bookSlot.put(booking.getDate(),slotsOfDay);
	}
	

	
	public void unsetBooking(Booking bookingToRemove){
		String date= bookingToRemove.getDate();
		TreeSet<Slot> slotsOfDay= this.bookSlot.get(date);
		slotsOfDay.remove(bookingToRemove.getSlot());
		return;
	}
	
	
	
	public String getConfRoomName() {
	 return this.confRoomName;
	}
	
	public int getBuildingId() {
		return this.buildingId;
	}
	
	public int getFloorId() {
		return this.floorId;
	}
	
}
