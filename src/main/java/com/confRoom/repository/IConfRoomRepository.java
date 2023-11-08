package com.confRoom.repository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;

import com.confRoom.model.ConfRoom;
import com.confRoom.model.Floor;
import com.confRoom.model.Slot;

public interface IConfRoomRepository {
	
	public  Map<Integer, ConfRoom> getConfRooms(Floor floor);
	public ConfRoom getConfRoomById(int buildingId,int floorId,int confRoomId);
	public ConfRoom addConfRoom(Floor floor,int maxCapacity,String confRoomName, int buildingId);
	public TreeSet<Slot> getBookedSlotsByDate(ConfRoom confRoom,String date);
	
}
