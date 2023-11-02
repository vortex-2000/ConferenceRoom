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


public class BookingService implements IBookingService{
	
	static public BookingRepository bookingRepo= BookingRepository.getInstance(); 
	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();
	static public UserRepository userRepo= UserRepository.getInstance(); 
	public IConfRoomRepository confRoomRepo= new ConfRoomRepository();
	public IFloorRepository floorRepo= new FloorRepository();
	
	

	
	private Boolean isValidDuration(Slot slot) throws ParseException {
		
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		Date currStartTime = parser.parse(slot.getSlotStartTime());
		Date currEndTime = parser.parse(slot.getSlotEndTime());
		
		
		long difference = (currEndTime.getTime() - currStartTime .getTime())/(60 * 60 * 1000) % 24;
		
		
		if(difference<0 && (24+difference)>12) return false;
		
		return difference<12;
		
	}
	
	
	private Boolean isRoomAvailable(ConfRoom confRoom,String date, Slot slot) throws ParseException {
		
		Map<String, TreeSet<Booking>> bookings= confRoom.getBookings();        // call 3 times instead of getting entire object 
		
		TreeSet<Booking> bookingsOfDay=bookings.get(date);			//get booking by date 
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");			
		Date currStartTime = parser.parse(slot.getSlotStartTime());
		Date currEndTime = parser.parse(slot.getSlotEndTime());

		LocalDate todayDate = LocalDate.parse(date);					//SPLIT METHOD
																		
		if(currEndTime.before(currStartTime)) {							//SPECIAL CASE (NEW TILL MIDNIGHT BOOKING): check next day
			
			LocalDate tomorrowDate=todayDate.plusDays(1);
			
			TreeSet<Booking> tomorrowBookings=bookings.get(tomorrowDate.toString());
			if(tomorrowBookings!=null) {
			Slot tomorrowSlot=tomorrowBookings.first().getSlot();
			Date tomorrowStartTime=parser.parse(tomorrowSlot.getSlotStartTime());
			
			if(tomorrowStartTime.before(currEndTime))
				return false;
			}
		}

		
							
		LocalDate yesterdayDate=todayDate.minusDays(1);			//SPECIAL CASE (NEW FROM MIDNIGHT BOOKING): check previous day
		
		TreeSet<Booking> yesterdayBookings=bookings.get(yesterdayDate.toString());
		if(yesterdayBookings!=null) {
		Slot yesterdaySlot=yesterdayBookings.last().getSlot();
		Date yesterdayEndTime=parser.parse(yesterdaySlot.getSlotEndTime());
		Date yesterdayStartTime=parser.parse(yesterdaySlot.getSlotStartTime());
		if(yesterdayEndTime.before(yesterdayStartTime) &&  yesterdayEndTime.after(currStartTime))
			return false;
		}
		

		
		if(bookingsOfDay==null)
			return true; 
			
		for(Booking bookingEntry : bookingsOfDay) {
			 
			
			Date startTime = parser.parse(bookingEntry.getSlot().getSlotStartTime());
			Date endTime = parser.parse(bookingEntry.getSlot().getSlotEndTime());
			
			//MIDNIGHT BOOKING CHECKS

			if(endTime.before(startTime) && (currStartTime.after(startTime) || currStartTime.equals(startTime) ||  currEndTime.after(startTime)))			
				return false;				//MIDNIGHT BOOKING ALREADY PRESENT

			if(currEndTime.before(currStartTime) && (currStartTime.before(startTime) || currStartTime.equals(startTime) || currStartTime.before(endTime)))		
				return false;				//NEW MIDNIGHT BOOKING 

			if(endTime.before(startTime) && currEndTime.before(currStartTime))
				return false;				//MULTIPLE MIDNIGHT BOOKING
			
			
			/*
			 * if(startTime.after(currEndTime)) //OPTIMIZATION break;  // AVOIDED BECAUSE OF MIDNIGHT CASES
			 */
			
			
			//NORMAL NON OVERLAP
			 if((currEndTime.before(startTime)) || (currStartTime.after(endTime))) continue;		

			 else if((currEndTime.equals(startTime)) || (currStartTime.equals(endTime))) continue;

			 else
				 return false;
		
		

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
	///////////////// RETURN booking object
	@SuppressWarnings("deprecation")
	public void bookConfRoom(int buildingId, int floorId, int confRoomId, int userId, int capacity, String date, Slot slot) throws ParseException {


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
			System.out.println("Size is less than your requirements, please try a different room"); // throw exception object not return 
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
	    
	    System.out.println("Booking Cancelled");				//string return
		
	}
	
	
	//MOVE TO CONFROOM REPO
	
	public void searchRooms(int buildingId, int floorId, String date,  Slot slot, int capacity) throws ParseException	
	{//list of room obj

		
		Floor floor = floorRepo.checkFloorPresence(buildingId, floorId);  //service
		
		if(floor==null)
			return;
		
		Map<Integer,ConfRoom> confRooms=floor.getConfRooms();
		
		Boolean roomFound=false;
		for(Map.Entry<Integer, ConfRoom> confRoomMap: confRooms.entrySet()) {
					
			ConfRoom confRoom= confRoomMap.getValue();
			
			if(isRoomAvailable(confRoom,date, slot) && isCapacitySufficient(confRoom, capacity)) {
				
				if(!roomFound)
					System.out.println("The following rooms are available for booking for slot " + slot.getSlotStartTime() + " - " + slot.getSlotEndTime() + " on the day " + date + " with your specific requirements:");
				
				System.out.println(confRoom.getConfRoomName());
				roomFound=true;
			}
		}
		
		if(!roomFound) {
			
			System.out.println("No Rooms are available for slot " + slot.getSlotStartTime() + " - " + slot.getSlotEndTime() + " on the day " + date + " with your specific requirements");
		}
	}
	
	public void suggestRooms(int buildingId, int floorId, String date,  Slot slot, int capacity, int days) throws ParseException {
		//list of room
		if(days>10)  //declare as constant  private NO_OF_DAYS
			System.out.println("We cannot provide data for more than 10 days");
		LocalDate currDate = LocalDate.parse(date);
		
		for(int i=0;i<days;i++) {
			
			
			String nextDateString= currDate.toString();
			currDate= currDate.plusDays(1);
			
			searchRooms(buildingId,floorId,nextDateString,slot,capacity);   	//append all res
		}	
	}
	
	
}
