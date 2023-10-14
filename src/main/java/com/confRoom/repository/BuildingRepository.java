package com.confRoom.repository;

import com.comfRoom.model.*;
import java.util.*;

public class BuildingRepository implements IBuildingRepository{
	
	public Map<Integer,Building> Buildings=new HashMap<Integer,Building>();
	
	public void AddBuilding(String name) {
		Building building= new Building(name);
		Buildings.put(building.getBuildingId(), building);
	}
	
	public void AddFloor(int buildingId) {
		 Floor floor=new Floor();
		 Buildings.get(buildingId).setFloor(floor);
	}
	
	public void AddConfRoom(int buildingId, int floorId,int maxCapacity) {
			ConfRoom confRoom=new ConfRoom(maxCapacity);
			Buildings.get(buildingId).getFloor(floorId).setConfRoom(confRoom);
	}
	
}
