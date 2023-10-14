package com.confRoom.model;
import java.util.*;

public class User {
	private int userId;
	private String name;
	
	private Map<Integer,Booking> bookings;
	
	public User(String name) {
		this.userId=(int)(Math.random()*1000);
		this.name=name;
		this.bookings= new HashMap<Integer,Booking>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public Map<Integer,Booking> getBookings(){
		return this.bookings;
	}
	
	public void setBookings(Booking booking) {
		this.bookings.put(booking.getBookingId(), booking);
	}
	
	public void unsetBookings(int bookingId) {
		this.bookings.remove(bookingId);
	}
}
