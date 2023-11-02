package com.confRoom.service;

import java.util.TreeSet;

import com.confRoom.model.Booking;
import com.confRoom.model.ConfRoom;
import com.confRoom.model.Floor;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.repository.ConfRoomRepository;
import com.confRoom.repository.FloorRepository;

public class ConfRoomService implements IConfRoomService{

	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	public ConfRoomRepository confRoomRepo= new ConfRoomRepository();
	public FloorRepository floorRepo= new FloorRepository();
	
	public int constructConfRoom(int id_b,int id_f,int capacity,String name) {
		
		Floor floor= floorRepo.checkFloorPresence(id_b,id_f);
		
		if(floor==null)
			return -1;
		String buildingName= buildingRepo.Buildings.get(id_b).getBuildingName();
		
		return confRoomRepo.addConfRoom(floor, capacity, name, buildingName);	
	}
	
	
	public void listAllBookings(int buildingId, int floorId, int confRoomID, String date) { //getAllBooking
		
		ConfRoom confRoom = confRoomRepo.checkConfRoomPresence(buildingId,floorId,confRoomID);
		
		 // logging in main and return list of booking
		
		TreeSet<Booking> bookings=confRoom.getBookings().get(date);
		
		if(bookings==null) {
			System.out.println("No Bookings for the day: " + date);
			return;
		}
		
		 for (Booking entry : bookings) {
	            System.out.println("Booking ID = " + entry.getBookingId() +  ", Slot time = " + entry.getSlot().getSlotStartTime() + " - " + entry.getSlot().getSlotEndTime());
	            System.out.println("*****************************************************************************************************************");
	            System.out.println();
		 }
	}
}
