package com.confRoom.repository;

import com.confRoom.model.Building;
import com.confRoom.model.Floor;

public class FloorRepository implements IFloorRepository{

	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	

	
	public Floor addFloor(Building building,String floorName) {
		 
		 Floor floor=new Floor(floorName);
		 building.setFloor(floor);
		 return floor;
	}
	
	public 	Floor getFloorById(int buildingId,int floorId) {
		
		return  buildingRepo.Buildings.get(buildingId).getFloor(floorId);
	}
	
}
