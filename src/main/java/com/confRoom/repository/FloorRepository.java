package com.confRoom.repository;

import com.confRoom.model.Building;
import com.confRoom.model.Floor;

public class FloorRepository {

	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	
	public Floor checkFloorPresence(int buildingId,int floorId) {
		
		Building building= buildingRepo.checkBuildingPresence(buildingId);
		if(building==null) 
			return null;
		Floor floor= building.getFloor(floorId);
		if(floor==null)
			System.out.println("The mentioned floor dosen't exists");
		return floor;
	}
	
	
	public int addFloor(Building building,String floorName) {
		 
		 Floor floor=new Floor(floorName);
		 building.setFloor(floor);
		 return floor.getFloorId();
	}
	
}
