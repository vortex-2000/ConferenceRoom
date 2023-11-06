package com.confRoom.repository;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;

import com.confRoom.model.Booking;
import com.confRoom.model.ConfRoom;
import com.confRoom.model.Floor;
import com.confRoom.model.Slot;
import com.confRoom.service.FloorService;

public class ConfRoomRepository implements IConfRoomRepository{
	

	
	public FloorRepository floorRepo= new FloorRepository();	//constructor
	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	
	public FloorService floorService= new FloorService();
	
	
	public ConfRoom getConfRoomById(int buildingId,int floorId,int confRoomId)
	{	
		return buildingRepo.Buildings.get(buildingId).getFloor(floorId).getConfRoom(confRoomId);
	}
	
	
	public ConfRoom addConfRoom(Floor floor,int maxCapacity,String confRoomName, int buildingId) {
		
		ConfRoom confRoom=new ConfRoom(maxCapacity,confRoomName, floor.getFloorId(),buildingId);
		floor.setConfRoom(confRoom);
		return confRoom;  
	}
	
	
	
		
	
}
