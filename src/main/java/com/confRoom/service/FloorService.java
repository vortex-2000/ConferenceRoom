package com.confRoom.service;

import com.confRoom.model.Building;
import com.confRoom.model.Floor;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.repository.FloorRepository;
import com.confRoom.repository.IBuildingRepository;
import com.confRoom.repository.IFloorRepository;


public class FloorService implements IFloorService {
	

	private IBuildingRepository buildingRepo;
	private IFloorRepository floorRepo;
	private IBuildingService buildingService;
	
	public FloorService() {
		buildingRepo= BuildingRepository.getInstance();
		floorRepo= new FloorRepository();
		buildingService = new BuildingService();
	}
	

	public Floor addFloor(int buildingId,String name) {
		
		
		if(!buildingService.isBuildingPresent(buildingId))
			return null;
		
		Building building = buildingRepo.getBuildingById(buildingId);
		
		return floorRepo.addFloor(building, name);
	}
	
	public Boolean isFloorPresent(int buildingId,int floorId) {
		
		if(!buildingService.isBuildingPresent(buildingId))
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
