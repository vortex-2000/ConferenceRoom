package com.confRoom.repository;

import com.confRoom.model.*;
import java.util.*;

public class BuildingRepository implements IBuildingRepository{
	
	public Map<Integer,Building> Buildings;
	
	private static BuildingRepository BuildingRepository_instance = null;
	
	private BuildingRepository() {
		Buildings=new HashMap<Integer,Building>();
	}
	
	 public static synchronized BuildingRepository getInstance() 
	    { 
	        if (BuildingRepository_instance == null) 
	        	BuildingRepository_instance = new BuildingRepository(); 
	  
	        return BuildingRepository_instance; 
	    } 
	 
	
	public int AddBuilding(String name) {
		Building building= new Building(name);
		Buildings.put(building.getBuildingId(), building);
		return building.getBuildingId();
		
	}
	
	public int AddFloor(Building building,String floorName) {
		 
		 Floor floor=new Floor(floorName);
		 building.setFloor(floor);
		 return floor.getFloorId();
	}
	
	public int AddConfRoom(Floor floor,int maxCapacity,String confRoomName) {
		
		ConfRoom confRoom=new ConfRoom(maxCapacity,confRoomName);
		floor.setConfRoom(confRoom);
		return confRoom.getConfRoomId();
	}
	
}
