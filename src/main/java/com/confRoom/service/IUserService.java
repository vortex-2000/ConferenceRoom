package com.confRoom.service;

public interface IUserService {
	
	public int registerUser(String name);
	public Boolean checkUserPresence(int userId);
}
