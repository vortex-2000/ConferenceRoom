package com.confRoom.service;

import com.confRoom.model.*;
import com.confRoom.repository.BookingRepository;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.repository.UserRepository;
import com.confRoom.repository.UtilRepository;

public class BookingService {
	
	static public BookingRepository bookingRepo= BookingRepository.getInstance(); 
	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	static public UserRepository userRepo= UserRepository.getInstance(); 
	
	public Boolean RoomAvailable(ConfRoom confRoom, int[]slot) {
		
		Boolean[] slots= confRoom.getSlots();
		
		for(int i=slot[0];i<=slot[1];i++) {
			if(slots[i]==true) {
				System.out.println("Slot Unavailable");
				return false;
			}
		}
		return true;
	}
	
	public Boolean sizeCheck(ConfRoom confRoom, int capacity) {
		
		if(confRoom.getMaxCapacity()<capacity) {
			System.out.println("Size is less than your requirements, please try a different room");
			return false;
		}
		return true;
	}
	
	public void BookConfRoom(int buildingId, int floorId, int confRoomId, int[]slot, int userId, int capacity) {
		
		ConfRoom confRoom = UtilRepository.CheckEntityPresence(buildingId,floorId,confRoomId);	
		if(confRoom==null)
			return;
		
		User user = UtilRepository.CheckUserPresence(userId);
		if(user==null)
			return;
		
		//Check Availability
		if(!RoomAvailable(confRoom, slot))
			return;
		
		if(!sizeCheck(confRoom, capacity))		
			return;

		Booking booking= new Booking(userId,confRoom);
				
		bookingRepo.AddBooking(confRoom, booking,user);
		
		System.out.println("Booking Completed");
	}
	
	public void CancelBooking(int bookingId) {
		
		Booking booking = UtilRepository.CheckBookingPresence(bookingId);
		
		if(booking==null) 
			return;
		
		User user = UtilRepository.CheckUserPresence(booking.getUserId());
		
	    bookingRepo.DeleteBooking(booking,user);
	    
	    System.out.println("Booking Cancelled");
		
	}
	
	
}
