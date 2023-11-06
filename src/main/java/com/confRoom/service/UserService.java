package com.confRoom.service;

import java.util.Map;

import com.confRoom.model.Booking;
import com.confRoom.model.ConfRoom;
import com.confRoom.model.User;
import com.confRoom.repository.BookingRepository;
import com.confRoom.repository.UserRepository;

public class UserService implements IUserService{
	
	static public UserRepository userRepo= UserRepository.getInstance(); 
	
	public int registerUser(String name) {
		int id_u=userRepo.addUser(name);
		return id_u;
	}
	
	public Boolean checkUserPresence(int userId) {
		
		if(!userRepo.Users.containsKey(userId))
		{
			System.out.println("The requested user is not present");
			return false;
		}
		return true;
	}
	
}
