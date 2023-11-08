package com.confRoom.service;

import com.confRoom.model.User;
import com.confRoom.repository.IUserRepository;
import com.confRoom.repository.UserRepository;

public class UserService implements IUserService{
	
	private IUserRepository userRepo; 
	
	public UserService() {
		userRepo= UserRepository.getInstance(); 
	}
	
	public User addUser(String name) {
		return userRepo.addUser(name);
	}
	
	public Boolean isUserPresent(int userId) {
		
		if(!userRepo.getUsers().containsKey(userId))
		{
			System.out.println("The requested user is not present");
			return false;
		}
		return true;
	}
	
}
