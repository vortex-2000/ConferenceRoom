package com.confRoom.service;
import com.confRoom.model.Building;

public interface IBuildingService {
	public Building addBuilding(String name);
	public Boolean isBuildingPresent(int buildingId);
	
}
