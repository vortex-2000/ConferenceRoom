package com.confRoom.service;

import com.confRoom.model.Floor;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.repository.ConfRoomRepository;
import com.confRoom.repository.FloorRepository;

public class ConfRoomService {

	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	public ConfRoomRepository confRoomRepo= new ConfRoomRepository();
	public FloorRepository floorRepo= new FloorRepository();
	
	public int constructConfRoom(int id_b,int id_f,int capacity,String name) {
		
		Floor floor= floorRepo.checkFloorPresence(id_b,id_f);
		
		if(floor==null)
			return -1;
		String buildingName= buildingRepo.Buildings.get(id_b).getBuildingName();
		
		return confRoomRepo.addConfRoom(floor, capacity, name, buildingName);	
	}
	
	/*
	 * public int searchConfRoom(int id_b,int id_f,int capacity) {
	 * 
	 * 
	 * }
	 */
}
