package com.confRoom.repository;

import com.confRoom.model.Building;

public interface IBuildingRepository {
	public Building checkBuildingPresence(int buildingId);
	public int addBuilding(String name);

}
