package com.confRoom.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;




public class ConfRoom {
	
	
	public class bookingComparator implements Comparator<Booking>{
		
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		java.util.Date date1;
		java.util.Date date2;
		public int compare(Booking b1, Booking s2) {
			
			try {
				date1 =  parser.parse(b1.getSlot().getSlotStartTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				date2 = parser.parse(s2.getSlot().getSlotEndTime());
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
	private Map<String, TreeSet<Booking>> bookings;
	int maxCapacity;
	String confRoomName;
	String buildingName;
	String floorName;
	
	
	public ConfRoom(int maxCapacity,String confRoomName, String floorName, String buildingName) {
		this.confRoomId= (int)(Math.random()*100);
		this.bookSlot= new HashMap<String, TreeSet<Slot>>();
		//Arrays.fill(this.slots, false);
		this.bookings=new HashMap<String, TreeSet<Booking>>();
		this.maxCapacity=maxCapacity;
		this.confRoomName=confRoomName;
		this.floorName=floorName;
		this.buildingName=buildingName;
	}
	
	public int getConfRoomId() {
		return this.confRoomId;
	}

	
	public Map<String,TreeSet<Booking>> getBookings(){
		return this.bookings;
	}
	
	public int getMaxCapacity() {
		return this.maxCapacity;
	}
	
	
	public void setBooking(Booking booking) {
		
		String date=booking.getDate();
		TreeSet<Booking> bookingsOfDay= this.bookings.get(date);
		
		if(bookingsOfDay==null)
			bookingsOfDay= new TreeSet<Booking>(new bookingComparator());
		
		bookingsOfDay.add(booking);
		
		this.bookings.put(booking.getDate(),bookingsOfDay);
	}
	

	
	public void unsetBooking(Booking bookingToRemove){
		String date= bookingToRemove.getDate();
		TreeSet<Booking> bookingsOfDay= this.bookings.get(date);
		bookingsOfDay.remove(bookingToRemove);
		return;
	}
	
	public String getAddress() {
		return "Address:    Building Name = " + this.buildingName + ", Floor Name = " + this.floorName + ", Conference Room Name = " + this.confRoomName;
	}
	
	public String getConfRoomName() {
	 return this.confRoomName;
	}
	
}
