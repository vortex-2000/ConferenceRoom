package com.confRoom.service;

import com.confRoom.model.*;
import com.confRoom.repository.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;

public class BookingService implements IBookingService {

	static public BookingRepository bookingRepo = BookingRepository.getInstance();
	static public BuildingRepository buildingRepo = BuildingRepository.getInstance();
	static public UserRepository userRepo = UserRepository.getInstance();
	public IConfRoomRepository confRoomRepo = new ConfRoomRepository();
	public IFloorRepository floorRepo = new FloorRepository();

	private Boolean isValidDuration(Slot slot) throws ParseException {

		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		Date currStartTime = parser.parse(slot.getSlotStartTime());
		Date currEndTime = parser.parse(slot.getSlotEndTime());

		long difference = (currEndTime.getTime() - currStartTime.getTime()) / (60 * 60 * 1000) % 24;

		if (difference < 0 && (24 + difference) > 12)
			return false;

		return difference < 12;

	}

	private Boolean isRoomAvailable(ConfRoom confRoom, String date, Slot slot) throws ParseException {

		Map<String, TreeSet<Slot>> slots = confRoom.getSlots(); // call 3 times instead of getting entire object

		TreeSet<Slot> slotsOfDay = slots.get(date); // get booking by date
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		Date currStartTime = parser.parse(slot.getSlotStartTime());
		Date currEndTime = parser.parse(slot.getSlotEndTime());

		LocalDate todayDate = LocalDate.parse(date); // SPLIT METHOD

		if (currEndTime.before(currStartTime)) { // SPECIAL CASE (NEW TILL MIDNIGHT BOOKING): check next day

			LocalDate tomorrowDate = todayDate.plusDays(1);

			TreeSet<Slot> tomorrowSlots = slots.get(tomorrowDate.toString());
			if (tomorrowSlots != null) {
				Slot tomorrowSlot = tomorrowSlots.first();
				Date tomorrowStartTime = parser.parse(tomorrowSlot.getSlotStartTime());

				if (tomorrowStartTime.before(currEndTime))
					return false;
			}
		}

		LocalDate yesterdayDate = todayDate.minusDays(1); // SPECIAL CASE (NEW FROM MIDNIGHT BOOKING): check previous
															// day

		TreeSet<Slot> yesterdayBookings = slots.get(yesterdayDate.toString());
		if (yesterdayBookings != null) {
			Slot yesterdaySlot = yesterdayBookings.last();
			Date yesterdayEndTime = parser.parse(yesterdaySlot.getSlotEndTime());
			Date yesterdayStartTime = parser.parse(yesterdaySlot.getSlotStartTime());
			if (yesterdayEndTime.before(yesterdayStartTime) && yesterdayEndTime.after(currStartTime))
				return false;
		}

		if (slotsOfDay == null)
			return true;

		for (Slot slotEntry : slotsOfDay) {

			Date startTime = parser.parse(slotEntry.getSlotStartTime());
			Date endTime = parser.parse(slotEntry.getSlotEndTime());

			// MIDNIGHT BOOKING CHECKS

			if (endTime.before(startTime) && (currStartTime.after(startTime) || currStartTime.equals(startTime)
					|| currEndTime.after(startTime)))
				return false; // MIDNIGHT BOOKING ALREADY PRESENT

			if (currEndTime.before(currStartTime) && (currStartTime.before(startTime) || currStartTime.equals(startTime)
					|| currStartTime.before(endTime)))
				return false; // NEW MIDNIGHT BOOKING

			if (endTime.before(startTime) && currEndTime.before(currStartTime))
				return false; // MULTIPLE MIDNIGHT BOOKING

			/*
			 * if(startTime.after(currEndTime)) //OPTIMIZATION break; // AVOIDED BECAUSE OF
			 * MIDNIGHT CASES
			 */

			// NORMAL NON OVERLAP
			if ((currEndTime.before(startTime)) || (currStartTime.after(endTime)))
				continue;

			else if ((currEndTime.equals(startTime)) || (currStartTime.equals(endTime)))
				continue;

			else
				return false;

		}

		return true;

	}

	private Boolean isCapacitySufficient(ConfRoom confRoom, int capacity) {
		// private
		if (confRoom.getMaxCapacity() < capacity) {
			return false;
		}
		return true;
	}

	private Boolean isValidFutureDate(LocalDate bookDate) throws ParseException {

		LocalDate maxFutureDate = LocalDate.now().plusDays(10);

		if (bookDate.isAfter(maxFutureDate)) {
			return false;
		}
		return true;
	}

	private Boolean isValidDate(LocalDate bookDate) throws ParseException {

		LocalDate currDate = LocalDate.now();

		if (bookDate.isBefore(currDate) || bookDate.isEqual(currDate)) {
			return false;
		}
		return true;
	}

	// TRY CATCH for runtime exceptions: generic message custom exception model
	// class
	///////////////// RETURN booking object
	@SuppressWarnings("deprecation")
	public Booking bookConfRoom(int buildingId, int floorId, int confRoomId, int userId, int capacity, String date,
			Slot slot) throws ParseException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		formatter = formatter.withLocale(Locale.ROOT);
		LocalDate dt = LocalDate.parse(date, formatter);

		if (!isValidDate(dt)) {
			System.out.println("New bookings starts from tomorrow.");
			return null;
		}

		if (!isValidFutureDate(dt)) {
			System.out.println("Booking can only be made for 10 days in future.");
			return null;
		}

		if (!isValidDuration(slot)) {
			System.out.println("Booking cannot be made for more than 12 hours.");
			return null;
		}

		ConfRoom confRoom = confRoomRepo.checkConfRoomPresence(buildingId, floorId, confRoomId); // Double DB call if
																									// not passed
		if (confRoom == null)
			return null;

		User user = userRepo.checkUserPresence(userId);
		if (user == null)
			return null;

		if (!isRoomAvailable(confRoom, date, slot)) {
			System.out.println("Sorry the required slot is already booked");
			return null;
		}

		if (!isCapacitySufficient(confRoom, capacity)) {
			System.out.println("Size is less than your requirements, please try a different room"); // throw exception
																									// object not return
			return null;
		}

		Booking booking = new Booking(userId, confRoom, date, slot);

		bookingRepo.addBooking(booking);

		return booking;
	}

	public Booking cancelBooking(int bookingId) {

		Booking booking = bookingRepo.checkBookingPresence(bookingId);

		if (booking == null)
			return null;

		bookingRepo.deleteBooking(booking);
		
		return booking;

	}

	// MOVE TO CONFROOM REPO

	public ArrayList<ConfRoom> searchRooms(int buildingId, int floorId, String date, Slot slot, int capacity) throws ParseException {// list
																														// of
																														// room
																														// obj

		Floor floor = floorRepo.checkFloorPresence(buildingId, floorId); // service

		if (floor == null)
			return null;

		Map<Integer, ConfRoom> confRooms = floor.getConfRooms();

		ArrayList<ConfRoom> confRoomsResult = new ArrayList<ConfRoom>();
		
		for (Map.Entry<Integer, ConfRoom> confRoomMap : confRooms.entrySet()) {

			ConfRoom confRoom = confRoomMap.getValue();

			if (isRoomAvailable(confRoom, date, slot) && isCapacitySufficient(confRoom, capacity)) {

				
				confRoomsResult.add(confRoom);
	
			}
		}

		
		
		return confRoomsResult;
	}

	public ArrayList<ArrayList<ConfRoom> > suggestRooms(int buildingId, int floorId, String date, Slot slot, int capacity, int days)
			throws ParseException {
		
		Floor floor = floorRepo.checkFloorPresence(buildingId, floorId); // service

		if (floor == null)
			return null;
		
		if (days > 10) { // declare as constant private NO_OF_DAYS
			System.out.println("We cannot provide data for more than 10 days");
			return null;
		}
		
		ArrayList<ArrayList<ConfRoom> > suggestedRooms = new ArrayList<ArrayList<ConfRoom> >();
		// list of room
		
		
		ArrayList<ConfRoom> confRooms = new ArrayList<ConfRoom>();
		// list of room
		
		
		LocalDate currDate = LocalDate.parse(date);

		for (int i = 0; i < days; i++) {

			String nextDateString = currDate.toString();
			currDate = currDate.plusDays(1);

			confRooms= searchRooms(buildingId, floorId, nextDateString, slot, capacity); // append all res
			
			suggestedRooms.add(confRooms);
		}
		return suggestedRooms;
	}

	public TreeSet<Booking> listAllBookings(int buildingId, int floorId, int confRoomID, String date) { //getAllBooking
		
	confRoomRepo.checkConfRoomPresence(buildingId,floorId,confRoomID);
		
		
		TreeSet<Booking> bookings=bookingRepo.getBookings(buildingId, floorId, confRoomID, date);
		
		if(bookings==null) {
			System.out.println("No Bookings for the day: " + date);
			return null;
		}
		
		 return bookings;
	}
	
	public Map<Integer,Booking> listBookingsOfUser(int userId) {
		
		Map<Integer,Booking> bookings= bookingRepo.listBookingsOfUser(userId);
		return bookings;
	}
	

}
