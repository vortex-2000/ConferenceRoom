import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

import com.confRoom.model.*;
import com.confRoom.service.*;


public class Program {
	
	static private IBuildingService buildingService = new BuildingService();
	static private IUserService userService = new UserService();
	static private IFloorService floorService = new FloorService();
	static private IConfRoomService confRoomService = new ConfRoomService();
	static private IBookingService bookingService = new BookingService();
	
	
	static private Scanner sc = new Scanner(System.in);

	static public void constructBuilding()
	{
		System.out.println("Enter a building name: ");
		sc.nextLine();
		String buildingName = sc.nextLine();
		Building newBuilding = buildingService.addBuilding(buildingName);
		System.out.println("A new building with name " + buildingName + " and Id " + newBuilding.getBuildingId() + " has been added");
	}

	
	static public void constructFloor()
	{
		System.out.println("Enter a building id: ");
		int buildingId = sc.nextInt();
		System.out.println("Enter a floor name: ");
		sc.nextLine();
		String floorName = sc.nextLine();
		Floor newFloor = floorService.addFloor(buildingId, floorName);
		
		if(newFloor==null)
			return;
		System.out.println("A new floor with name " + floorName + " and Id " + newFloor.getFloorId() + " has been added");
	}
	
	static public void constructConfRoom()
	{
		System.out.println("Enter a building id: ");
		sc = new Scanner(System.in);
		int buildingId = sc.nextInt();
		System.out.println("Enter a floor id: ");
		int floorId = sc.nextInt();
		System.out.println("Enter a capacity: ");
		int capacity = sc.nextInt();
		System.out.println("Enter a conference room name: ");
		sc.nextLine();
		String roomName = sc.nextLine();
		ConfRoom newConfRoom = confRoomService.addConfRoom(buildingId, floorId, capacity, roomName);
		if (newConfRoom==null)
			return;
		
		System.out.println("A new conference room with name " + roomName + " and Id " + newConfRoom.getConfRoomId() + " has been added");
	}
	
	static public void registerUser()
	{
		System.out.println("Enter your name: ");
		sc.nextLine();
		String userName = sc.nextLine();
		User user = userService.addUser(userName);
		System.out.println("A new user with name " + userName + " and Id " + user.getUserId()+ " has been added");
	}
	
	static public void bookRoom() throws ParseException
	{
		System.out.println("Enter a building id: ");
		sc = new Scanner(System.in);
		int buildingId = sc.nextInt();
		System.out.println("Enter a floor id: ");
		int floorId = sc.nextInt();
		System.out.println("Enter a conference room id: ");
		int roomId = sc.nextInt();
		System.out.println("Enter your user id: ");
		int userId = sc.nextInt();
		System.out.println("Enter expected capacity: ");
		int capacity = sc.nextInt();
		Slot slot = new Slot();
		System.out.println("Enter booking starting time in HH:mm format:");
		sc.nextLine();
		slot.setSlotStartTime(sc.nextLine()); // slot class
		System.out.println("Enter booking ending time in HH:mm format: ");
		slot.setSlotEndTime(sc.nextLine());
		System.out.println("Enter the date in yyyy-mm-dd format: ");
		String date = sc.nextLine();
		Booking newBooking=bookingService.addBooking(buildingId, floorId, roomId, userId, capacity, date, slot);
		if(newBooking==null)
			return;
		
		System.out.println("Booking Completed. Your booking id is: " + newBooking.getBookingId());
	}
	
	static public void cancelBooking() {
		System.out.println("Enter a booking id: ");
		sc = new Scanner(System.in);
		int bkid = sc.nextInt();
		Booking cancelledBooking=bookingService.deleteBooking(bkid);
		if(cancelledBooking==null)
			return;
		
		System.out.println("Booking Cancelled with booking id: " + cancelledBooking.getBookingId());
	}
	
	static public void listBookingsByUser() {
		System.out.println("Enter a user id: ");
		sc = new Scanner(System.in);
		int userId = sc.nextInt();
		TreeSet<Booking> bookingsByUser= bookingService.getBookingsByUser(userId);
		if(bookingsByUser!=null) {
			
		 for (Booking entry : bookingsByUser) { 
	            System.out.println("Booking ID = " + entry.getBookingId() + ", Date = " + entry.getDate() + ", Slot time = " + entry.getSlot().getSlotStartTime() + " - " + entry.getSlot().getSlotEndTime());
	            ConfRoom confRoom= entry.getConfRoom();
	            
	            String address=confRoomService.getAddress(confRoom);
	            System.out.println(address);
	            System.out.println("*****************************************************************************************************************");
	            System.out.println(); 
		 }
		}
	}
	
	static public void listBookingsOfConfRoom() {
		System.out.println("Enter a building id: ");
		sc = new Scanner(System.in);
		int buildingId = sc.nextInt();
		System.out.println("Enter a floor id: ");
		int floorId = sc.nextInt();
		System.out.println("Enter a conference room id: ");
		int roomId = sc.nextInt();
		System.out.println("Enter the date in yyyy-mm-dd format: ");
		sc.nextLine();
		String date = sc.nextLine();
		TreeSet<Booking> bookings=bookingService.getBookingsByRoom(buildingId, floorId, roomId, date);
		
		for (Booking entry : bookings) {
            System.out.println("Booking ID = " + entry.getBookingId() +  ", Slot time = " + entry.getSlot().getSlotStartTime() + " - " + entry.getSlot().getSlotEndTime());
            System.out.println("*****************************************************************************************************************");
            System.out.println();
		}
	}
	
	static public void searchRoom() throws ParseException {
		System.out.println("Enter a building id: ");
		sc= new Scanner(System.in);
		int buildingId= sc.nextInt();
		System.out.println("Enter a floor id: ");
		int floorId= sc.nextInt(); 
		System.out.println("Enter a capacity: ");
		int capacity= sc.nextInt();
		Slot slot = new Slot();
		System.out.println("Enter booking starting time in HH:mm format:");
		sc.nextLine();
		slot.setSlotStartTime(sc.nextLine()); 
		System.out.println("Enter booking ending time in HH:mm format: ");
		slot.setSlotEndTime(sc.nextLine());
		System.out.println("Enter the date in yyyy-mm-dd format: ");
		String date= sc.nextLine(); 
		ArrayList<ConfRoom> confRooms=confRoomService.getRoomsByRequirements(buildingId,floorId,date,slot,capacity);
		
		if(confRooms==null)
			return;
		
		if (confRooms.isEmpty()) {

			System.out.println("No Rooms are available for slot " + slot.getSlotStartTime() + " - "
					+ slot.getSlotEndTime() + " on the day " + date + " with your specific requirements");
		}
		else {
			System.out.println("The following rooms are available for booking for slot "
					+ slot.getSlotStartTime() + " - " + slot.getSlotEndTime() + " on the day " + date
					+ " with your specific requirements:");
			
			
			for(ConfRoom confRoom: confRooms)
			{
				System.out.println(confRoom.getConfRoomName());
			}
		}
	}
	
	static public void suggestRoom() throws ParseException {
		System.out.println("Enter a building id: ");
		sc= new Scanner(System.in);
		int buildingId= sc.nextInt();
		System.out.println("Enter a floor id: ");
		int floorId= sc.nextInt(); 
		System.out.println("Enter a capacity: ");
		int capacity= sc.nextInt();
		Slot slot = new Slot();
		System.out.println("Enter booking starting time in HH:mm format:");
		sc.nextLine();
		slot.setSlotStartTime(sc.nextLine()); 
		System.out.println("Enter booking ending time in HH:mm format: ");
		slot.setSlotEndTime(sc.nextLine());
		System.out.println("Enter number of days for which you want to search ");
		int days= sc.nextInt(); 
		System.out.println("Enter the suggestion start date in yyyy-mm-dd format: ");
		sc.nextLine();
		String date= sc.nextLine(); 
		ArrayList<ArrayList<ConfRoom>> suggestedConfRooms=confRoomService.getSuggestedRooms(buildingId,floorId,date,slot,capacity,days);
		
		if(suggestedConfRooms==null)
			return;
		LocalDate currDate = LocalDate.parse(date);
		for(ArrayList<ConfRoom> confRoomsOfDay:suggestedConfRooms) {
			
			if(confRoomsOfDay.isEmpty()) {
				System.out.println("No Rooms are available for slot " + slot.getSlotStartTime() + " - "
						+ slot.getSlotEndTime() + " on the day " + date + " with your specific requirements");
				continue;
			}
			String currDateString = currDate.toString();
			
			System.out.println("The following rooms are available for booking for slot "
					+ slot.getSlotStartTime() + " - " + slot.getSlotEndTime() + " on the day " + currDateString
					+ " with your specific requirements:");
			
			for (ConfRoom confRoom:confRoomsOfDay) {

				System.out.println(confRoom.getConfRoomName());
					
				currDate = currDate.plusDays(1);
			}
		}
	}
	
	
	public static void main(String[] args) throws ParseException {

		int action = 0;

		while (action != -1) {
			System.out.println("Enter the action you want to perform: ");
			action = sc.nextInt();

			switch (action) {

			case 1:
				constructBuilding();
				break;

			case 2:
				constructFloor();
				break;

			case 3:
				constructConfRoom();
				break;

			case 4:
				registerUser();
				break;

			case 5:
				bookRoom();
				break;

			case 6: 		// CANCEL
				cancelBooking();
				break;

			case 7: 		// LIST BY USER
				listBookingsByUser();
				break;

			case 8: 		// LIST ALL BOOKINGS OF A CONFROOM IN A GIVEN DAY
				listBookingsOfConfRoom();
				break;

			case 9:			//SEARCH ANY ROOM IN GIVEN ADDRESS AND SLOT WITH SPECIFIC CAPACITY 
				searchRoom();
				break;
				
			case 10:		// SUGGEST ANY ROOM IN SAME SLOT AND CAPACITY FOR GIVEN DAYS
				suggestRoom();
				break;
			

			case -1:
				System.out.println("Thank you");
				break;

			default:
				System.out.println("Enter correct code.");

			}

		}

	}

}
