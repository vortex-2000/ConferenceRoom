package com.confRoom.service;

import com.confRoom.model.*;
import com.confRoom.repository.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;


public class BookingService {
	
	static public BookingRepository bookingRepo= BookingRepository.getInstance(); 
	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	static public UserRepository userRepo= UserRepository.getInstance(); 
	public ConfRoomRepository confRoomRepo= new ConfRoomRepository();
	public FloorRepository floorRepo= new FloorRepository();
	
	

	
	private Boolean isValidDuration(String[]slot) throws ParseException {
		
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		
		Date currStartTime = parser.parse(slot[0]);
		Date currEndTime = parser.parse(slot[1]);
		
		
		long difference = (currEndTime.getTime() - currStartTime .getTime())/(60 * 60 * 1000) % 24;
		
		
		if(difference<0 && (24+difference)>12) return false;
		
		return difference<12;
		
	}
	
	
	private Boolean isRoomAvailable(ConfRoom confRoom,String date, String[]slot) throws ParseException {
		
		Map<String, TreeSet<Slot>> slots= confRoom.getSlots();
		
		TreeSet<Slot> slotsOfDay=slots.get(date);
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		Date currStartTime = parser.parse(slot[0]);
		Date currEndTime = parser.parse(slot[1]);
		
		LocalDate todayDate = LocalDate.parse(date);
		
		if(currEndTime.before(currStartTime)) {							//SPECIAL CASE (NEW TILL MIDNIGHT BOOKING): check next day
			
			LocalDate tomorrowDate=todayDate.plusDays(1);
			
			TreeSet<Slot> tomorrowSlots=slots.get(tomorrowDate.toString());
			if(tomorrowSlots!=null) {
			Slot tomorrowSlot=tomorrowSlots.first();
			Date tomorrowStartTime=parser.parse(tomorrowSlot.getSlotStartTime());
			
			return tomorrowStartTime.after(currEndTime);
			}
		}
		
		
							
		LocalDate yesterdayDate=todayDate.minusDays(1);			//SPECIAL CASE (NEW FROM MIDNIGHT BOOKING): check previous day
		
		TreeSet<Slot> yesterdaySlots=slots.get(yesterdayDate.toString());
		if(yesterdaySlots!=null) {
		Slot yesterdaySlot=yesterdaySlots.last();
		Date yesterdayEndTime=parser.parse(yesterdaySlot.getSlotEndTime());
		if(yesterdayEndTime.after(currStartTime))
			return false;
		}
		
		if(slotsOfDay==null)
			return true; 
			
		for(Slot slotEntry : slotsOfDay) {
			 
			
			Date startTime = parser.parse(slotEntry.getSlotStartTime());
			Date endTime = parser.parse(slotEntry.getSlotEndTime());
			

			if(endTime.before(startTime) || currEndTime.before(currStartTime)) {										//SPECIAL CASE (EXISTING MIDNIGHT BOOKING) check if our new booking is after this booking
				if(currStartTime.after(startTime) || currEndTime.after(startTime)) 										//+
					return false;																						//SPECIAL CASE (NEW TILL MIDNIGHT BOOKING) check if existing midnight booking
																
			}
			
			
			 if(startTime.after(currEndTime)) //OPTIMIZATION  
				  break;
			  
			 if((currEndTime.after(startTime)) || (currStartTime.before(endTime))) return false;
			  
		

		}
		
		return true;
		
	}
	
	private Boolean isCapacitySufficient(ConfRoom confRoom, int capacity) {
		//private
		if(confRoom.getMaxCapacity()<capacity) {
			return false;
		}
		return true;
	}
	
	private Boolean isValidFutureDate(LocalDate bookDate) throws ParseException {
		
		LocalDate maxFutureDate=LocalDate.now().plusDays(10);	
		
		if(bookDate.isAfter(maxFutureDate)) {
			return false;
		}
		return true;
	}
	
	private Boolean isValidDate(LocalDate bookDate) throws ParseException {
		
		LocalDate currDate=LocalDate.now();	
		
		if(bookDate.isBefore(currDate) || bookDate.isEqual(currDate)) {
			return false;
		}
		return true;
	}
	
	
	//TRY CATCH for runtime exceptions: generic message custom exception model class 
	///////////////// RETURN ID
	@SuppressWarnings("deprecation")
	public void bookConfRoom(int buildingId, int floorId, int confRoomId, int userId, int capacity, String date, String[]slot) throws ParseException {
		
		
		////////////////////////////////////////////SLOT KO ARRAY MAT RAKHO
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		formatter = formatter.withLocale( Locale.ROOT );
		LocalDate dt = LocalDate.parse(date, formatter);
		
		if(!isValidDate(dt)) {
			System.out.println("New bookings starts from tomorrow.");
			return;
		}
		
		if(!isValidFutureDate(dt)) {
			System.out.println("Booking can only be made for 10 days in future.");
			return;
		}
		

		
		if(!isValidDuration(slot)) {
			System.out.println("Booking cannot be made for more than 12 hours.");
			return;
		}
	
		ConfRoom confRoom = confRoomRepo.checkConfRoomPresence(buildingId,floorId,confRoomId);	// Double DB call if not passed
		if(confRoom==null)
			return;
		
		User user = userRepo.checkUserPresence(userId);
		if(user==null)
			return;
		
	
		if(!isRoomAvailable(confRoom,date, slot)) {
			 System.out.println("Sorry the required slot is already booked");
			return;
		}
		
		if(!isCapacitySufficient(confRoom, capacity)) {
			System.out.println("Size is less than your requirements, please try a different room");
			return;
		}

		Booking booking= new Booking(userId,confRoom,date,slot);
				
		bookingRepo.addBooking(booking);
		
		System.out.println("Booking Completed. Your booking id is: " + booking.getBookingId());
	}
	
	public void cancelBooking(int bookingId) {
		
		Booking booking = bookingRepo.checkBookingPresence(bookingId);
		
		if(booking==null) 
			return;
		
	    bookingRepo.deleteBooking(booking);
	    
	    System.out.println("Booking Cancelled");
		
	}
	
	public void listBookings(int userId) {
		
		User user = userRepo.checkUserPresence(userId);
		
		Map<Integer,Booking> bookings=user.getBookings();
		
		 for (Map.Entry<Integer,Booking> entry : bookings.entrySet()) { 
	            System.out.println("Booking ID = " + entry.getKey() + ", Date = " + entry.getValue().getDate() + ", Slot time = " + entry.getValue().getSlot()[0] + " - " + entry.getValue().getSlot()[1]);
	            System.out.println(entry.getValue().getConfRoom().getAddress());
	            System.out.println("*****************************************************************************************************************");
	            System.out.println();
		 }
	}
	
	
	public void searchRooms(int buildingId, int floorId, String date,  String[] slot, int capacity) throws ParseException	
	{

		
		Floor floor = floorRepo.checkFloorPresence(buildingId, floorId);
		
		if(floor==null)
			return;
		
		Map<Integer,ConfRoom> confRooms=floor.getConfRooms();
		
		Boolean roomFound=false;
		for(Map.Entry<Integer, ConfRoom> confRoomMap: confRooms.entrySet()) {
					
			ConfRoom confRoom= confRoomMap.getValue();
			
			if(isRoomAvailable(confRoom,date, slot) && isCapacitySufficient(confRoom, capacity)) {
				
				if(!roomFound)
					System.out.println("The following rooms are available for booking:");
				
				System.out.println(confRoom.getConfRoomName());
				roomFound=true;
			}
		}
		
		if(!roomFound)
			System.out.println("No Room found according to your requirements.");
	}
	
	
}
