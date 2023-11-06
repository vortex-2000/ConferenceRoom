package com.confRoom.repository;

import com.confRoom.model.Building;
import com.confRoom.model.Floor;

public interface IFloorRepository {

	public 	Floor getFloorById(int buildingId,int floorId);
	public Floor addFloor(Building building,String floorName);
}
