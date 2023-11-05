package com.confRoom.service;

import java.util.TreeSet;

import com.confRoom.model.Booking;
import com.confRoom.model.ConfRoom;
import com.confRoom.model.Floor;
import com.confRoom.model.Slot;
import com.confRoom.repository.BookingRepository;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.repository.ConfRoomRepository;
import com.confRoom.repository.FloorRepository;

public class ConfRoomService implements IConfRoomService{

	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	static public BookingRepository bookingRepo= BookingRepository.getInstance();

	public ConfRoomRepository confRoomRepo= new ConfRoomRepository();
	public FloorRepository floorRepo= new FloorRepository();
	
	public ConfRoom constructConfRoom(int id_b,int id_f,int capacity,String name) {
		
		Floor floor= floorRepo.checkFloorPresence(id_b,id_f);
		
		if(floor==null)
			return null;
	
		
		return confRoomRepo.addConfRoom(floor, capacity, name, id_b);	
	}
	
	
	
}
