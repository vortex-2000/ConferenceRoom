package com.confRoom.repository;

import com.confRoom.model.User;

public interface IUserRepository {
	public User checkUserPresence(int userId) ;
	public int addUser(String name);
}
