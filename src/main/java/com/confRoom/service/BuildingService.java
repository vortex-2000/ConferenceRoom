package com.confRoom.service;

import com.confRoom.model.Building;
import com.confRoom.model.Floor;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.repository.ConfRoomRepository;
import com.confRoom.repository.FloorRepository;


public class BuildingService implements IBuildingService {
	
	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	public ConfRoomRepository confRoomRepo;//private
	public FloorRepository floorRepo;
	
	
	public BuildingService()
	{
		this.confRoomRepo=new ConfRoomRepository();  ////////// best practice
		this.floorRepo= new FloorRepository();
	}
	
	public Building constructBuilding(String name) {	//addBuilding
		 //no underscore in naming local variable (camel case)
		return buildingRepo.addBuilding(name);								// return building object
		
	}
	
	public Building getBuilding(int id)
	{
		return buildingRepo.Buildings.get(id);
	}

}
