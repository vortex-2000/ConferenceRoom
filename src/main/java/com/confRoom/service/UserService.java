package com.confRoom.service;

import com.confRoom.repository.BookingRepository;
import com.confRoom.repository.UserRepository;

public class UserService {
	
	static public UserRepository userRepo= UserRepository.getInstance(); 
	
	public int RegisterUser(String name) {
		int id_u=userRepo.AddUser(name);
		System.out.println("A new user with name " + name + " and Id " + id_u + " has been added");
		return id_u;
	}
}
