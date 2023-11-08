package com.confRoom.repository;

import java.util.Map;

import com.confRoom.model.User;

public interface IUserRepository {
	public Map<Integer,User> getUsers();
	public User getUserById(int userId) ;
	public User addUser(String name);
}
