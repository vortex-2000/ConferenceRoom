package com.confRoom.service;

import com.confRoom.model.Floor;

public interface IFloorService {
	
	public Floor addFloor(int buildingId,String name);
	public Boolean isFloorPresent(int buildingId,int floorId);
}
