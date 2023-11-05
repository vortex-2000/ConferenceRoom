package com.confRoom.service;

import com.confRoom.model.Building;
import com.confRoom.model.Floor;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.repository.FloorRepository;


public class FloorService implements IFloorService {
	

	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	public FloorRepository floorRepo= new FloorRepository();
	

	public Floor constructFloor(int id_b,String name) {
		
		Building building = buildingRepo.checkBuildingPresence(id_b);
		if(building==null)
			return null;
		
		return floorRepo.addFloor(building, name);
	}
}
