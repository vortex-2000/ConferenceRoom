package com.confRoom.model;

public class User {
	private int userId;
	private String name;
	

	public User(String name) {
		this.userId=(int)(Math.random()*1000);
		this.name=name;
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
}
