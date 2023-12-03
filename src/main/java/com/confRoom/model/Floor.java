package com.confRoom.model;
import java.util.*;

public class Floor {
	
	private int floorId;
	private String floorName;
	private Map<Integer,ConfRoom> confRooms;
	
	public Floor(String name) {
		this.floorId= (int)(Math.random()*100);
		this.confRooms= new HashMap<Integer,ConfRoom>();
		this.floorName=name;
	}
	
	public void setConfRoom(ConfRoom confRoom) {
		this.confRooms.put(confRoom.getConfRoomId(), confRoom);
	}
	
	public ConfRoom getConfRoom(int confRoomId) {
		return this.confRooms.get(confRoomId);
	}
	
	public int getFloorId() {
		return this.floorId;
	}
	
	public String getFloorName() {
		return this.floorName;
	}
	
	public Map<Integer,ConfRoom> getConfRooms(){
		return this.confRooms;
	}
}
