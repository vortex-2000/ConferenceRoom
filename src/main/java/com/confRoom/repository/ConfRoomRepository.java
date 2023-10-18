package com.confRoom.repository;

import com.confRoom.model.ConfRoom;
import com.confRoom.model.Floor;

public class ConfRoomRepository {
	
	public FloorRepository floorRepo= new FloorRepository();
	
	public ConfRoom checkConfRoomPresence(int buildingId,int floorId, int confRoomId) {
		
		Floor floor= floorRepo.checkFloorPresence(buildingId,floorId);
		if(floor==null) 
			return null;
		ConfRoom confRoom= floor.getConfRoom(confRoomId);
		if(confRoom==null)
			System.out.println("The mentioned conference room dosen't exists");
		return confRoom;
	}
 
	
	public int addConfRoom(Floor floor,int maxCapacity,String confRoomName) {
		
		ConfRoom confRoom=new ConfRoom(maxCapacity,confRoomName);
		floor.setConfRoom(confRoom);
		return confRoom.getConfRoomId();
	}
	
	public void getConfRooms(Floor floor) {
		
	}
}
