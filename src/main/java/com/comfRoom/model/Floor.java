package com.comfRoom.model;
import java.util.*;

public class Floor {
	
	private int floorId;
	private Map<Integer,ConfRoom> confRooms;
	
	public Floor() {
		this.floorId= (int)Math.random()*100;
		this.confRooms= new HashMap<Integer,ConfRoom>();
	}
	
	/*
	 * public ConfRoom getConfRooms() { return this.confRooms; }
	 */
	
	public void setConfRoom(ConfRoom confRoom) {
		this.confRooms.put(confRoom.getConfRoomId(), confRoom);
	}
	
	public ConfRoom getConfRoom(int confRoomId) {
		return this.confRooms.get(confRoomId);
	}
	
	public int getFloorId() {
		return this.floorId;
	}
}
