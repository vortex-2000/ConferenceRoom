package com.confRoom.repository;

import java.util.HashMap;
import java.util.Map;

import com.comfRoom.model.Booking;
import com.comfRoom.model.Building;
import com.comfRoom.model.ConfRoom;

public class BookingRepository implements IBookingRepository{
	
	public Map<Integer,Booking> Bookings=new HashMap<Integer,Booking>();
	
	public void AddBooking(ConfRoom confRoom,Booking booking) {
	
		Bookings.put(booking.getBookingId(), booking);
		confRoom.setBooking(booking);
		confRoom.setSlots(booking.getSlot());
	}
	
	public Booking DeleteBooking(Booking booking) {

		ConfRoom confRoom=booking.getConfRoom();
		confRoom.unsetBooking(booking.getBookingId());
		confRoom.unsetSlots(booking.getSlot());
		return booking;
	}
	
}
