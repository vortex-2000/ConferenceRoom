package com.confRoom.repository;

import com.confRoom.model.ConfRoom;
import com.confRoom.model.Floor;

public interface IConfRoomRepository {
	public ConfRoom checkConfRoomPresence(int buildingId,int floorId, int confRoomId);
	public ConfRoom addConfRoom(Floor floor,int maxCapacity,String confRoomName, int buildingId);
}
