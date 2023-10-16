package com.confRoom.service;

import com.confRoom.model.Building;
import com.confRoom.model.Floor;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.repository.ConfRoomRepository;
import com.confRoom.repository.FloorRepository;


public class BuildingService {
	
	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	public ConfRoomRepository confRoomRepo= new ConfRoomRepository();
	public FloorRepository floorRepo= new FloorRepository();
	
	public int constructBuilding(String name) {
		int id_b=buildingRepo.addBuilding(name);
		return id_b;
		
	}

}
