package com.confRoom.service;

import java.text.ParseException;

import com.confRoom.model.Slot;

public interface IBookingService {
	
	public void bookConfRoom(int buildingId, int floorId, int confRoomId, int userId, int capacity, String date, Slot slot) throws ParseException;
	public void cancelBooking(int bookingId); 
	public void searchRooms(int buildingId, int floorId, String date,  Slot slot, int capacity) throws ParseException;
	public void suggestRooms(int buildingId, int floorId, String date,  Slot slot, int capacity, int days) throws ParseException ;
	public void listAllBookings(int buildingId, int floorId, int confRoomID, String date);
	public void listBookingsOfUser(int userId);
}
