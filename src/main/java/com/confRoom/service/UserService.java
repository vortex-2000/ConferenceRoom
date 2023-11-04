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
		System.out.println("A new user with name " + name + " and Id " + id_u + " has been added");
		return id_u;
	}
	
}
