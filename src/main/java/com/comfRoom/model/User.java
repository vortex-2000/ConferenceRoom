package com.comfRoom.model;

public class User {
	int userId;
	String name;
	
	public User(String name) {
		this.userId=(int)Math.random()*1000;
		this.name=name;
	}
	
	public String getName() {
		return this.name;
	}
	
}
