package com.confRoom.service;

import java.text.ParseException;
import java.util.ArrayList;

import com.confRoom.model.ConfRoom;
import com.confRoom.model.Slot;

public interface IConfRoomService {

	
	public Boolean isConfRoomPresent(int buildingId,int floorId, int confRoomId);
	public ConfRoom addConfRoom(int id_b,int id_f,int capacity,String name);
	public ArrayList<ConfRoom> getRooms(int buildingId, int floorId, String date,  Slot slot, int capacity) throws ParseException;
	public ArrayList<ArrayList<ConfRoom> > getSuggestedRooms(int buildingId, int floorId, String date,  Slot slot, int capacity, int days) throws ParseException ;
	public String getAddress(ConfRoom confRoom);
}
