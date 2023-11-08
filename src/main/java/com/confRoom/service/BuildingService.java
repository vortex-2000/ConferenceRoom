package com.confRoom.service;

import com.confRoom.model.Building;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.repository.IBuildingRepository;


public class BuildingService implements IBuildingService {
	
	private IBuildingRepository buildingRepo;
	
	public BuildingService()
	{
		buildingRepo= BuildingRepository.getInstance();
	}
	
	public Building addBuilding(String name) {	
		
		return buildingRepo.addBuilding(name);								
		
	}
	
	
 	public Boolean isBuildingPresent(int buildingId) {               
 		if(!buildingRepo.getBuildings().containsKey(buildingId)) {
 			System.out.println("The requested building is not present");
			return false;
 		}
 		return true;
	}

}
