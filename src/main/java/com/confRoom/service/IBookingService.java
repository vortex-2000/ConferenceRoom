package com.confRoom.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.confRoom.model.Booking;
import com.confRoom.model.ConfRoom;
import com.confRoom.model.Slot;

public interface IBookingService {
	
	public Booking bookConfRoom(int buildingId, int floorId, int confRoomId, int userId, int capacity, String date, Slot slot) throws ParseException;
	public Booking cancelBooking(int bookingId); 
	public ArrayList<ConfRoom> searchRooms(int buildingId, int floorId, String date,  Slot slot, int capacity) throws ParseException;
	public ArrayList<ArrayList<ConfRoom> > suggestRooms(int buildingId, int floorId, String date,  Slot slot, int capacity, int days) throws ParseException ;
	public TreeSet<Booking> listAllBookings(int buildingId, int floorId, int confRoomID, String date);
	public Map<Integer,Booking> listBookingsOfUser(int userId);
}
