package com.confRoom.repository;

import java.util.HashMap;
import java.util.Map;

import com.confRoom.model.*;


public class BookingRepository implements IBookingRepository{
	
	public Map<Integer,Booking> Bookings=new HashMap<Integer,Booking>();
	
	private static BookingRepository BookingRepository_instance = null;
	
		private BookingRepository() {
			Bookings=new HashMap<Integer,Booking>();
		}
	
	 public static synchronized BookingRepository getInstance() 
	    { 
	        if (BookingRepository_instance == null) 
	        	BookingRepository_instance = new BookingRepository(); 
	  
	        return BookingRepository_instance; 
	    } 
	 
	
	public void AddBooking(ConfRoom confRoom,Booking booking,User user) {
		
		Bookings.put(booking.getBookingId(), booking);
		
		user.setBookings(booking);

		confRoom.setBooking(booking);
		confRoom.setSlots(booking.getSlot());
	}
	
	public void DeleteBooking(Booking booking,User user) {
		
		Bookings.remove(booking.getBookingId());
		
		user.unsetBookings(booking.getBookingId());
		
		ConfRoom confRoom=booking.getConfRoom();
		confRoom.unsetBooking(booking.getBookingId());
		confRoom.unsetSlots(booking.getSlot());

		
	}
	
}
