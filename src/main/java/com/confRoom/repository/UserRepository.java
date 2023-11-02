package com.confRoom.repository;

import java.util.HashMap;
import java.util.Map;

import com.confRoom.model.Booking;
import com.confRoom.model.User;

public class UserRepository implements IUserRepository{
	public Map<Integer,User> Users;
	
	private static UserRepository UserRepository_instance = null;
	
	private UserRepository() {
		Users=new HashMap<Integer,User>();
	}

	public static synchronized UserRepository getInstance() 
    { 
        if (UserRepository_instance == null) 
        	UserRepository_instance = new UserRepository(); 
  
        return UserRepository_instance; 
    }
	
	public User checkUserPresence(int userId) {
		
		User user= Users.get(userId);
		if(user==null)
			System.out.println("The mentioned user dosen't exists");
		return user;
	}
	
 
	
	public int addUser(String name){	
		User user= new User(name);
		Users.put(user.getUserId(), user);
		return user.getUserId();
	}
}
