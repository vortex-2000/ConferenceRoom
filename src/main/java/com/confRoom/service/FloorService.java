package com.confRoom.service;

import com.confRoom.model.Building;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.repository.FloorRepository;


public class FloorService {
	

	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	public FloorRepository floorRepo= new FloorRepository();
	

	public int constructFloor(int id_b,String name) {
		
		Building building = buildingRepo.checkBuildingPresence(id_b);
		if(building==null)
			return -1;
		
		return floorRepo.addFloor(building, name);
	}
}
