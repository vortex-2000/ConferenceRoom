package com.confRoom.model;

import java.util.*;

public class Building {
	
	private int buildingId;
	private String buildingName;
	private Map<Integer,Floor>floors;
	
	public Building(String name){
		this.buildingId=(int)(Math.random()*100);
		this.buildingName=name;
		this.floors= new HashMap<Integer,Floor>();
	}
	
	public int getBuildingId() {
		return this.buildingId;
	}
	
	public void setFloor(Floor floor) {
		this.floors.put(floor.getFloorId(), floor);
	}
	
	public Floor getFloor(int floorId) {
		return this.floors.get(floorId); 
	}
	
	public String getBuildingName() {
		return this.buildingName;
	}
}
