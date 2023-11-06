package com.confRoom.service;

import com.confRoom.model.Floor;

public interface IFloorService {
	
	public Floor constructFloor(int id_b,String name);
	public Boolean checkFloorPresence(int buildingId,int floorId);
}
