package com.confRoom.service;

public interface IUserService {
	
	public int registerUser(String name);
	public void listBookingsOfUser(int userId);
}
