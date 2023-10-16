package com.confRoom.service;

import java.util.Arrays;

import com.confRoom.model.*;
import com.confRoom.repository.BookingRepository;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.repository.ConfRoomRepository;
import com.confRoom.repository.FloorRepository;
import com.confRoom.repository.UserRepository;


public class BookingService {
	
	static public BookingRepository bookingRepo= BookingRepository.getInstance(); 
	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	static public UserRepository userRepo= UserRepository.getInstance(); 
	public ConfRoomRepository confRoomRepo= new ConfRoomRepository();
	
	private Boolean roomAvailable(ConfRoom confRoom, int[]slot) {
		
		Boolean[] slots= confRoom.getSlots();
		
		for(int i=slot[0]+1;i<=slot[1];i++) {
			if(slots[i]==true) {
				System.out.println("Slot Unavailable");
				return false;
			}
		}
		return true;
	}
	
	private Boolean isCapacitySufficient(ConfRoom confRoom, int capacity) { //isCapacityAvailable   (IS) START BOOL METHOD
		//private
		if(confRoom.getMaxCapacity()<capacity) {
			System.out.println("Size is less than your requirements, please try a different room");
			return false;
		}
		return true;
	}
	//TRY CATCH for runtime exceptions: generic message custom exception model class
	public void bookConfRoom(int buildingId, int floorId, int confRoomId, int[]slot, int userId, int capacity) {
		
		ConfRoom confRoom = confRoomRepo.checkConfRoomPresence(buildingId,floorId,confRoomId);	// Double DB call if not passed
		if(confRoom==null)
			return;
		
		User user = userRepo.checkUserPresence(userId);
		if(user==null)
			return;
		
	
		if(!roomAvailable(confRoom, slot))
			return;
		
		if(!isCapacitySufficient(confRoom, capacity))		
			return;

		Booking booking= new Booking(userId,confRoom,slot);
				
		bookingRepo.addBooking(booking);
		
		System.out.println("Booking Completed. Your booking id is: " + booking.getBookingId());
	}
	
	public void cancelBooking(int bookingId) {
		
		Booking booking = bookingRepo.checkBookingPresence(bookingId);
		
		if(booking==null) 
			return;
		
	    bookingRepo.deleteBooking(booking);
	    
	    System.out.println("Booking Cancelled");
		
	}
	
	
}
