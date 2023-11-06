package com.confRoom.service;

import com.confRoom.model.Building;
import com.confRoom.model.Floor;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.repository.FloorRepository;


public class FloorService implements IFloorService {
	

	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	public FloorRepository floorRepo= new FloorRepository();
	static public BuildingService buildingService = new BuildingService();
	

	public Floor constructFloor(int id_b,String name) {
		
		
		if(!buildingService.checkBuildingPresence(id_b))
			return null;
		
		Building building = buildingRepo.getBuildingById(id_b);
		
		return floorRepo.addFloor(building, name);
	}
	
	public Boolean checkFloorPresence(int buildingId,int floorId) {
		
		if(!buildingService.checkBuildingPresence(buildingId))
		{
			return false;
		}
		
		if(floorRepo.getFloorById(buildingId, floorId)==null) {
			System.out.println("The requested floor is not present");
			return false;
		}
		return true;
	}
	
}
