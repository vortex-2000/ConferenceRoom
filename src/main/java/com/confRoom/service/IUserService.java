package com.confRoom.service;

import com.confRoom.model.User;

public interface IUserService {
	
	public User addUser(String name);
	public Boolean isUserPresent(int userId);
}
