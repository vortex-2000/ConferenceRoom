package com.confRoom.service;
import com.confRoom.model.Building;

public interface IBuildingService {
	public Building constructBuilding(String name);
	public Boolean checkBuildingPresence(int buildingId);
	
}
