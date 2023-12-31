package com.confRoom.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeSet;
import com.confRoom.model.Building;
import com.confRoom.model.ConfRoom;
import com.confRoom.model.Floor;
import com.confRoom.model.Slot;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.repository.ConfRoomRepository;
import com.confRoom.repository.FloorRepository;
import com.confRoom.repository.IBuildingRepository;
import com.confRoom.repository.IConfRoomRepository;
import com.confRoom.repository.IFloorRepository;

public class ConfRoomService implements IConfRoomService{

	private static final int MAX_DAYS=10;
	private IBuildingRepository buildingRepo;

	private IConfRoomRepository confRoomRepo;
	private IFloorRepository floorRepo;


	private IFloorService floorService; 


	public ConfRoomService() {
		buildingRepo= BuildingRepository.getInstance();
		confRoomRepo= new ConfRoomRepository();
		floorRepo= new FloorRepository();
		floorService = new FloorService();
	}


	private Boolean isNonOverlapTomorrowYesterday(String date,Date currStartTime,Date currEndTime,ConfRoom confRoom ) throws ParseException {

		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

		LocalDate todayDate = LocalDate.parse(date); // SPLIT METHOD

		if (currEndTime.before(currStartTime)) { // SPECIAL CASE (NEW TILL MIDNIGHT BOOKING): check next day

			LocalDate tomorrowDate = todayDate.plusDays(1);

			TreeSet<Slot> tomorrowSlots = confRoomRepo.getBookedSlotsByDate(confRoom, tomorrowDate.toString());
			if (tomorrowSlots != null) {
				Slot tomorrowSlot = tomorrowSlots.first();
				Date tomorrowStartTime = parser.parse(tomorrowSlot.getSlotStartTime());

				if (tomorrowStartTime.before(currEndTime))
					return false;
			}
		}

		LocalDate yesterdayDate = todayDate.minusDays(1); // SPECIAL CASE (NEW FROM MIDNIGHT BOOKING): check previous
		// day

		TreeSet<Slot> yesterdayBookings = confRoomRepo.getBookedSlotsByDate(confRoom, yesterdayDate.toString());
		if (yesterdayBookings != null) {
			Slot yesterdaySlot = yesterdayBookings.last();
			Date yesterdayEndTime = parser.parse(yesterdaySlot.getSlotEndTime());
			Date yesterdayStartTime = parser.parse(yesterdaySlot.getSlotStartTime());
			if (yesterdayEndTime.before(yesterdayStartTime) && yesterdayEndTime.after(currStartTime))
				return false;
		}
		return true;

	}

	public Boolean isRoomAvailable(ConfRoom confRoom, String date, Slot slot) throws ParseException {

		// call 3 times instead of getting entire object

		TreeSet<Slot> slotsOfDay = confRoomRepo.getBookedSlotsByDate(confRoom, date); // get booking by date
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		Date currStartTime = parser.parse(slot.getSlotStartTime());
		Date currEndTime = parser.parse(slot.getSlotEndTime());

		if(!isNonOverlapTomorrowYesterday(date,currStartTime,currEndTime,confRoom))
			return false;


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
	public  Boolean isCapacitySufficient(ConfRoom confRoom, int capacity) {

		if (confRoom.getMaxCapacity() < capacity) {
			return false;
		}
		return true;
	}


	public Boolean isConfRoomPresent(int buildingId,int floorId, int confRoomId) {

		if(!floorService.isFloorPresent(buildingId,floorId))
			return false;

		if(confRoomRepo.getConfRoomById(buildingId, floorId, confRoomId)==null)
		{
			System.out.println("The requested room is not present");
			return false;
		}
		return true;
	}



	public ConfRoom addConfRoom(int buildingId,int floorId,int capacity,String name) {



		if(!floorService.isFloorPresent(buildingId,floorId))
			return null;

		Floor floor= floorRepo.getFloorById(buildingId, floorId);
		return confRoomRepo.addConfRoom(floor, capacity, name, buildingId);	
	}


	public ArrayList<ConfRoom> getRoomsByRequirements(int buildingId, int floorId, String date, Slot slot, int capacity) throws ParseException {

		if(!floorService.isFloorPresent(buildingId, floorId))
			return null;

		Floor floor = floorRepo.getFloorById(buildingId, floorId); 
		if (floor == null)
			return null;

		Map<Integer, ConfRoom> confRooms = confRoomRepo.getConfRooms(floor);

		ArrayList<ConfRoom> confRoomsResult = new ArrayList<ConfRoom>();

		for (Map.Entry<Integer, ConfRoom> confRoomMap : confRooms.entrySet()) {

			ConfRoom confRoom = confRoomMap.getValue();

			if (isRoomAvailable(confRoom, date, slot) && isCapacitySufficient(confRoom, capacity)) {


				confRoomsResult.add(confRoom);

			}
		}

		return confRoomsResult;
	}

	public ArrayList<ArrayList<ConfRoom> > getSuggestedRooms(int buildingId, int floorId, String date, Slot slot, int capacity, int days)
			throws ParseException {


		if (!floorService.isFloorPresent(buildingId, floorId))
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

			confRooms= getRoomsByRequirements(buildingId, floorId, nextDateString, slot, capacity); 

			suggestedRooms.add(confRooms);
		}
		return suggestedRooms;
	}

	public String getAddress(ConfRoom confRoom)
	{
		Building building = buildingRepo.getBuildingById(confRoom.getBuildingId());
		return "Address:    Building Name = " + building.getBuildingName() + ", Floor Name = " + building.getFloor(confRoom.getFloorId()).getFloorName() + ", Conference Room Name = " + confRoom.getConfRoomName();
	}

}
