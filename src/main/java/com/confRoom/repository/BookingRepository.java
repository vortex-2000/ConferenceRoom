package com.confRoom.repository;

import java.util.HashMap;
import java.util.Map;

import com.confRoom.model.*;


public class BookingRepository implements IBookingRepository{
	
	public Map<Integer,Booking> Bookings=new HashMap<Integer,Booking>();
	static public UserRepository userRepo= UserRepository.getInstance();
	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();

	
	
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
	 
	 public Booking checkBookingPresence(int bookingId) {
			
			Booking booking= Bookings.get(bookingId);
			if(booking==null)
				System.out.println("The mentioned booking dosen't exists");
			return booking;
		}
	
	public void addBooking(Booking booking) { 
		ConfRoom confRoom = booking.getConfRoom();
		User user = userRepo.Users.get(booking.getUserId());
		
		Bookings.put(booking.getBookingId(), booking);
		
		user.setBookings(booking);

		confRoom.setBooking(booking);
		confRoom.setSlots(booking.getDate(),booking.getSlot());
	}
	
	public void deleteBooking(Booking booking) { 
		
		Bookings.remove(booking.getBookingId());
		User user = userRepo.checkUserPresence(booking.getUserId());
		user.unsetBookings(booking.getBookingId());
		Slot slotToRemove= new Slot();
		slotToRemove.setSlotTime(booking.getSlot());
		
		ConfRoom confRoom=booking.getConfRoom();
		confRoom.unsetBooking(booking.getBookingId());
		confRoom.unsetSlots(booking.getDate(),slotToRemove);

		
	}
	
}
