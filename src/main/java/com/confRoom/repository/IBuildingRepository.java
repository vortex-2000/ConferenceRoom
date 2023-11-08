package com.confRoom.repository;

import java.util.Map;
import java.util.TreeSet;

import com.confRoom.model.Booking;
import com.confRoom.model.Building;

public interface IBuildingRepository {
	public Building getBuildingById(int id);
	public Building addBuilding(String name);
	public Map<Integer,Building> getBuildings();
}
