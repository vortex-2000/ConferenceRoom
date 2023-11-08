package com.confRoom.repository;

import java.util.Map;
import java.util.TreeSet;

import com.confRoom.model.Booking;

public interface IBookingRepository {
	public Map<Integer,Booking> getBookings();
	public Booking getBookingById(int bookingId);
	public Booking addBooking(Booking booking);
	public Booking deleteBooking(Booking booking);
	public TreeSet<Booking> getBookingsByUser(int userId);
	public TreeSet<Booking> getBookingsByRoom(int buildingId, int floorId, int confRoomID, String date);

}
