package com.confRoom.service;

import java.text.ParseException;
import java.util.ArrayList;

import com.confRoom.model.ConfRoom;
import com.confRoom.model.Slot;

public interface IConfRoomService {

	
	public Boolean checkConfRoomPresence(int buildingId,int floorId, int confRoomId);
	public ConfRoom constructConfRoom(int id_b,int id_f,int capacity,String name);
	public ArrayList<ConfRoom> searchRooms(int buildingId, int floorId, String date,  Slot slot, int capacity) throws ParseException;
	public ArrayList<ArrayList<ConfRoom> > suggestRooms(int buildingId, int floorId, String date,  Slot slot, int capacity, int days) throws ParseException ;
	
}
