package com.confRoom.repository;

import com.confRoom.model.Booking;

public interface IBookingRepository {
	public void addBooking(Booking booking);
	public void deleteBooking(Booking booking);
	public Booking checkBookingPresence(int bookingId); 
}
