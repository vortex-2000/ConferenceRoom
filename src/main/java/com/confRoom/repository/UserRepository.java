package com.confRoom.repository;

import java.util.HashMap;
import java.util.Map;

import com.confRoom.model.Booking;
import com.confRoom.model.User;

public class UserRepository implements IUserRepository{
	private Map<Integer,User> users;
	
	private static UserRepository UserRepository_instance = null;
	
	private UserRepository() {
		users=new HashMap<Integer,User>();
	}

	public static synchronized UserRepository getInstance() 
    { 
        if (UserRepository_instance == null) 
        	UserRepository_instance = new UserRepository(); 
  
        return UserRepository_instance; 
    }
	
	public User getUserById(int userId) {
		
		User user= users.get(userId);
		return user;
	}
	
 
	
	public User addUser(String name){	
		User user= new User(name);
		users.put(user.getUserId(), user);
		return user;
	}
	
	public Map<Integer,User> getUsers(){
		return this.users;
	}
}
