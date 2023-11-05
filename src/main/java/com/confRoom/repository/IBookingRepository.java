package com.confRoom.repository;

import java.util.Map;

import com.confRoom.model.Booking;

public interface IBookingRepository {
	public void addBooking(Booking booking);
	public void deleteBooking(Booking booking);
	public Booking checkBookingPresence(int bookingId); 
	public Map<Integer,Booking> listBookingsOfUser(int userId);
}
