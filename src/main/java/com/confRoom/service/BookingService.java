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
	
	
	
	private Boolean isValidTime(String[]slot) throws ParseException {
		
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		
		Date currStartTime = parser.parse(slot[0]);
		Date currEndTime = parser.parse(slot[1]);
		
		long difference = (currEndTime.getTime() - currStartTime .getTime())/(60 * 60 * 1000) % 24;
		
		
		if(difference<0 && (24+difference)>12) return false;
		
		return difference<12;
		
	}
	
	
	private Boolean roomAvailable(ConfRoom confRoom,String date, String[]slot) throws ParseException {
		
		Map<String, TreeSet<Slot>> slots= confRoom.getSlots();
		
		TreeSet<Slot> slotsOfDay=slots.get(date);
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		Date currStartTime = parser.parse(slot[0]);
		Date currEndTime = parser.parse(slot[1]);
		
		
		
		if(currEndTime.before(currStartTime)) {
			LocalDate todayDate = LocalDate.parse(date);
			LocalDate tomorrowDate=todayDate.plusDays(1);
			
			TreeSet<Slot> tomorrowSlots=slots.get(tomorrowDate.toString());
			if(tomorrowSlots==null) return true;
			Slot tomorrowSlot=tomorrowSlots.first();
			Date tomorrowStartTime=parser.parse(tomorrowSlot.getSlotStartTime());
			
			return tomorrowStartTime.after(currEndTime);
		}
		
		if(slotsOfDay==null)
			return true; 
			
		for(Slot slotEntry : slotsOfDay) {
			 
			
			Date startTime = parser.parse(slotEntry.getSlotStartTime());
			Date endTime = parser.parse(slotEntry.getSlotEndTime());
			
			
			 if(startTime.after(currEndTime)) //OPTIMIZATION  
				  break;
			  
			  if((currEndTime.before(startTime)) || (currStartTime.after(endTime))) continue;
			  
			  else if((currEndTime.equals(startTime)) || (currStartTime.equals(endTime))) continue;
			  
			  else {
				  return false;
			  }
		}
		
		return true;
	}
	
	private Boolean isCapacitySufficient(ConfRoom confRoom, int capacity) { //isCapacityAvailable   (IS) START BOOL METHOD
		//private
		if(confRoom.getMaxCapacity()<capacity) {
			System.out.println("Size is less than your requirements, please try a different room");
			return false;
		}
		return true;
	}
	
	private Boolean isValidDate(LocalDate bookDate) throws ParseException {
		
		LocalDate maxFutureDate=LocalDate.now().plusDays(10);	
		
		if(bookDate.isAfter(maxFutureDate)) {
			return false;
		}
		return true;
	}
	
	
	//TRY CATCH for runtime exceptions: generic message custom exception model class
	@SuppressWarnings("deprecation")
	public void bookConfRoom(int buildingId, int floorId, int confRoomId, int userId, int capacity, String date, String[]slot) throws ParseException {
		
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		formatter = formatter.withLocale( Locale.ROOT );
		LocalDate dt = LocalDate.parse(date, formatter);
		
		if(!isValidDate(dt)) {
			System.out.println("Booking can only be made for 10 days in future");
			return;
		}
		
		if(!isValidTime(slot)) {
			System.out.println("Booking cannot be made for more than 12 hours.");
			return;
		}
	
		ConfRoom confRoom = confRoomRepo.checkConfRoomPresence(buildingId,floorId,confRoomId);	// Double DB call if not passed
		if(confRoom==null)
			return;
		
		User user = userRepo.checkUserPresence(userId);
		if(user==null)
			return;
		
	
		if(!roomAvailable(confRoom,date, slot)) {
			 System.out.println("Sorry the required slot is already booked");
			return;
		}
		
		if(!isCapacitySufficient(confRoom, capacity))		
			return;

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
	
	
}
