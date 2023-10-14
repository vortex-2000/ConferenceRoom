package com.confRoom.service;

import com.confRoom.model.Building;
import com.confRoom.model.Floor;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.repository.UtilRepository;

public class BuildingService {
	
	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	
	public void ConstructBuilding(String name) {
		int id_b=buildingRepo.AddBuilding(name);
		System.out.println("A new building with name " + name + " and Id " + id_b + " has been added");
	}
	
	public void ConstructFloor(int id_b,String name) {
		
		Building building = UtilRepository.CheckEntityPresence(id_b);
		if(building==null)
			return;
		
		int id_f=buildingRepo.AddFloor(building, name);
		System.out.println("A new floor with name " + name + " and Id " + id_f + " has been added");
	}
	

	public void ConstructConfRoom(int id_b,int id_f,int capacity,String name) {
		
		Floor floor= UtilRepository.CheckEntityPresence(id_b,id_f);
		if(floor==null)
			return;
		
		int id_c=buildingRepo.AddConfRoom(floor, capacity, name);	
		System.out.println("A new conference room with name " + name + " and Id " + id_c + " has been added");
	}
	
	

}
