package com.confRoom.service;

import java.util.Map;

import com.confRoom.model.Booking;
import com.confRoom.model.User;
import com.confRoom.repository.BookingRepository;
import com.confRoom.repository.UserRepository;

public class UserService implements IUserService{
	
	static public UserRepository userRepo= UserRepository.getInstance(); 
	
	public int registerUser(String name) {
		int id_u=userRepo.addUser(name);
		System.out.println("A new user with name " + name + " and Id " + id_u + " has been added");
		return id_u;
	}
	//repo 
	public void listBookingsOfUser(int userId) {
		
		User user = userRepo.checkUserPresence(userId);
		
		if(user==null)
			return;
		
		Map<Integer,Booking> bookings=user.getBookings();
		
		if(bookings==null) {
			System.out.println("No Bookings for the mentioned user");
			return;
		}
		
		 for (Map.Entry<Integer,Booking> entry : bookings.entrySet()) { 
	            System.out.println("Booking ID = " + entry.getKey() + ", Date = " + entry.getValue().getDate() + ", Slot time = " + entry.getValue().getSlot().getSlotStartTime() + " - " + entry.getValue().getSlot().getSlotEndTime());
	            System.out.println(entry.getValue().getConfRoom().getAddress());
	            System.out.println("*****************************************************************************************************************");
	            System.out.println();
		 }
	}
}
