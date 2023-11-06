package com.confRoom.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
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

	private static final int MAX_DAYS=10;
	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	static public BookingRepository bookingRepo= BookingRepository.getInstance();

	public ConfRoomRepository confRoomRepo= new ConfRoomRepository();
	public FloorRepository floorRepo= new FloorRepository();
	
	public BookingService bookingService =new BookingService();
	public FloorService floorService = new FloorService(); 
	
	
	public Boolean checkConfRoomPresence(int buildingId,int floorId, int confRoomId) {
		
		if(!floorService.checkFloorPresence(buildingId,floorId))
			return false;
		
		if(confRoomRepo.getConfRoomById(buildingId, floorId, confRoomId)==null)
		{
			System.out.println("The requested room is not present");
			return false;
		}
		return true;
	}
	

	
	public ConfRoom constructConfRoom(int id_b,int id_f,int capacity,String name) {
		
		
		
		if(!floorService.checkFloorPresence(id_b,id_f))
			return null;
	
		Floor floor= floorRepo.getFloorById(id_b, id_f);
		return confRoomRepo.addConfRoom(floor, capacity, name, id_b);	
	}
	
	// MOVE TO CONFROOM REPO

			public ArrayList<ConfRoom> searchRooms(int buildingId, int floorId, String date, Slot slot, int capacity) throws ParseException {// list
																																// of
																																// room
																																// obj

				if(!floorService.checkFloorPresence(buildingId, floorId))// service
						return null;
				
				Floor floor = floorRepo.getFloorById(buildingId, floorId); 
				if (floor == null)
					return null;

				Map<Integer, ConfRoom> confRooms = floor.getConfRooms();

				ArrayList<ConfRoom> confRoomsResult = new ArrayList<ConfRoom>();
				
				for (Map.Entry<Integer, ConfRoom> confRoomMap : confRooms.entrySet()) {

					ConfRoom confRoom = confRoomMap.getValue();

					if (bookingService.isRoomAvailable(confRoom, date, slot) && bookingService.isCapacitySufficient(confRoom, capacity)) {

						
						confRoomsResult.add(confRoom);
			
					}
				}

				
				
				return confRoomsResult;
			}

			public ArrayList<ArrayList<ConfRoom> > suggestRooms(int buildingId, int floorId, String date, Slot slot, int capacity, int days)
					throws ParseException {
				

				if (!floorService.checkFloorPresence(buildingId, floorId))
					return null;
				
				if (days > MAX_DAYS) {
					System.out.println("We cannot provide data for more than 10 days");
					return null;
				}
				
				ArrayList<ArrayList<ConfRoom> > suggestedRooms = new ArrayList<ArrayList<ConfRoom> >();
				
				
				ArrayList<ConfRoom> confRooms = new ArrayList<ConfRoom>();
				
				
				LocalDate currDate = LocalDate.parse(date);

				for (int i = 0; i < days; i++) {

					String nextDateString = currDate.toString();
					currDate = currDate.plusDays(1);

					confRooms= searchRooms(buildingId, floorId, nextDateString, slot, capacity); 
					
					suggestedRooms.add(confRooms);
				}
				return suggestedRooms;
			}
	
	
	
}
