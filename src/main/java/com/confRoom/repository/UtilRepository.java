package com.confRoom.repository;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.model.*;

public class UtilRepository {
	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	static public UserRepository userRepo= UserRepository.getInstance(); 
	static public BookingRepository bookingRepo= BookingRepository.getInstance(); 
	
	static public Building CheckEntityPresence(int buildingId) {
		Building building=buildingRepo.Buildings.get(buildingId);		
		if(building==null)
			System.out.println("The mentioned building dosen't exists");
		return building;
	}
	
	static public Floor CheckEntityPresence(int buildingId,int floorId) {
		
		Building building= CheckEntityPresence(buildingId);
		if(building==null) 
			return null;
		Floor floor= building.getFloor(floorId);
		if(floor==null)
			System.out.println("The mentioned floor dosen't exists");
		return floor;
	}
	
	static public ConfRoom CheckEntityPresence(int buildingId,int floorId, int confRoomId) {
		
		Floor floor= CheckEntityPresence(buildingId,floorId);
		if(floor==null) 
			return null;
		ConfRoom confRoom= floor.getConfRoom(confRoomId);
		if(confRoom==null)
			System.out.println("The mentioned conference room dosen't exists");
		return confRoom;
	}
	
	static public User CheckUserPresence(int userId) {
		
		User user= userRepo.Users.get(userId);
		if(user==null)
			System.out.println("The mentioned user dosen't exists");
		return user;
	}
	
	static public Booking CheckBookingPresence(int bookingId) {
		
		Booking booking= bookingRepo.Bookings.get(bookingId);
		if(booking==null)
			System.out.println("The mentioned booking dosen't exists");
		return booking;
	}
	
}
