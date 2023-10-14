package com.confRoom.service;

import com.comfRoom.model.Booking;
import com.comfRoom.model.ConfRoom;
import com.confRoom.repository.BookingRepository;
import com.confRoom.repository.BuildingRepository;

public class BookingService {
	
	BookingRepository bookingRepo= new BookingRepository();
	BuildingRepository buildingRepo= new BuildingRepository();
	
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
		
		ConfRoom confRoom = buildingRepo.Buildings.get(buildingId).getFloor(floorId).getConfRoom(confRoomId);
		//Check Availability
		if(!RoomAvailable(confRoom, slot)) { 
			return;
		}
		
		if(!sizeCheck(confRoom, capacity))
		{			
			return;
		}
		Booking booking= new Booking(userId,confRoom);
				
		bookingRepo.AddBooking(confRoom, booking);
		
		System.out.println("Booking Completed");
	}
	
	public void CancelBooking(int bookingId) {
		
		Booking booking = bookingRepo.Bookings.get(bookingId);
		
		if(booking==null) 
		{ 
			System.out.println("No booking Exists");
			return;
		}
		
	    bookingRepo.DeleteBooking(booking);
	    
	    System.out.println("Booking Cancelled");
		
	}
}
