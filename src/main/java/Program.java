import java.text.ParseException;
import java.util.Scanner;

import com.confRoom.model.Slot;
import com.confRoom.repository.BuildingRepository;
import com.confRoom.service.BookingService;
import com.confRoom.service.BuildingService;
import com.confRoom.service.ConfRoomService;
import com.confRoom.service.FloorService;
import com.confRoom.service.UserService;
import com.confRoom.service.*;


public class Program {
	
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		IBuildingService buildingService = new BuildingService();
		IUserService userService = new UserService();
		IFloorService floorService = new FloorService();
		IConfRoomService confRoomService = new ConfRoomService();
		IBookingService bookingService = new BookingService();

		Scanner sc = new Scanner(System.in); // System.in is a standard input stream.

		int action = 0;

		while (action != -1) {
			System.out.println("Enter the action you want to perform: ");
			action = sc.nextInt();

			switch (action) {

			case 1:
				System.out.println("Enter a building name: ");
				sc.nextLine();
				String bstr = sc.nextLine();
				int id_b = buildingService.constructBuilding(bstr);
				System.out.println("A new building with name " + bstr + " and Id " + id_b + " has been added");
				break;

			case 2:
				System.out.println("Enter a building id: ");
				int bid = sc.nextInt();
				System.out.println("Enter a floor name: ");
				sc.nextLine();
				String fstr = sc.nextLine();
				int id_f = floorService.constructFloor(bid, fstr);
				if (id_f != -1)
					System.out.println("A new floor with name " + fstr + " and Id " + id_f + " has been added");
				break;

			case 3:
				System.out.println("Enter a building id: ");
				sc = new Scanner(System.in);
				bid = sc.nextInt();
				System.out.println("Enter a floor id: ");
				int fid = sc.nextInt();
				System.out.println("Enter a capacity: ");
				int capacity = sc.nextInt();
				System.out.println("Enter a conference room name: ");
				sc.nextLine();
				String cstr = sc.nextLine();
				int id_c = confRoomService.constructConfRoom(bid, fid, capacity, cstr);
				if (id_c != -1)
					System.out
							.println("A new conference room with name " + cstr + " and Id " + id_c + " has been added");
				break;

			case 4:
				System.out.println("Enter your name: ");
				sc.nextLine();
				String ustr = sc.nextLine();
				int uid = userService.registerUser(ustr);
				break;

			case 5:
				System.out.println("Enter a building id: ");
				sc = new Scanner(System.in);
				bid = sc.nextInt();
				System.out.println("Enter a floor id: ");
				fid = sc.nextInt();
				System.out.println("Enter a conference room id: ");
				int cid = sc.nextInt();
				System.out.println("Enter your user id: ");
				uid = sc.nextInt();
				System.out.println("Enter expected capacity: ");
				capacity = sc.nextInt();
				Slot slot = new Slot();
				System.out.println("Enter booking starting time in HH:mm format:");
				sc.nextLine();
				slot.setSlotStartTime(sc.nextLine()); // slot class
				System.out.println("Enter booking ending time in HH:mm format: ");
				slot.setSlotEndTime(sc.nextLine());
				System.out.println("Enter the date in yyyy-mm-dd format: ");
				String date = sc.nextLine();
				bookingService.bookConfRoom(bid, fid, cid, uid, capacity, date, slot);
				break;

			case 6: // CANCEL
				System.out.println("Enter a booking id: ");
				sc = new Scanner(System.in);
				int bkid = sc.nextInt();
				bookingService.cancelBooking(bkid);
				break;

			case 7: // LIST BY USER
				System.out.println("Enter a user id: ");
				sc = new Scanner(System.in);
				uid = sc.nextInt();
				userService.listBookingsOfUser(uid);
				break;

			case 8: // LIST ALL BOOKINGS OF A CONFROOM IN A GIVEN DAY
				System.out.println("Enter a building id: ");
				sc = new Scanner(System.in);
				bid = sc.nextInt();
				System.out.println("Enter a floor id: ");
				fid = sc.nextInt();
				System.out.println("Enter a conference room id: ");
				cid = sc.nextInt();
				System.out.println("Enter the date in yyyy-mm-dd format: ");
				sc.nextLine();
				date = sc.nextLine();
				confRoomService.listAllBookings(bid, fid, cid, date);
				break;

			case 9:			//SEARCH ANY ROOM IN GIVEN ADDRESS AND SLOT WITH SPECIFIC CAPACITY 
				System.out.println("Enter a building id: ");
				sc= new Scanner(System.in);
				bid= sc.nextInt();
				System.out.println("Enter a floor id: ");
				fid= sc.nextInt(); 
				System.out.println("Enter a capacity: ");
				capacity= sc.nextInt();
				slot = new Slot();
				System.out.println("Enter booking starting time in HH:mm format:");
				sc.nextLine();
				slot.setSlotStartTime(sc.nextLine()); // slot class
				System.out.println("Enter booking ending time in HH:mm format: ");
				slot.setSlotEndTime(sc.nextLine());
				System.out.println("Enter the date in yyyy-mm-dd format: ");
				date= sc.nextLine(); 
				bookingService.searchRooms(bid,fid,date,slot,capacity);
				break;
				
			case 10:		// SUGGEST ANY ROOM IN SAME SLOT AND CAPACITY FOR GIVEN DAYS
				System.out.println("Enter a building id: ");
				sc= new Scanner(System.in);
				bid= sc.nextInt();
				System.out.println("Enter a floor id: ");
				fid= sc.nextInt(); 
				System.out.println("Enter a capacity: ");
				capacity= sc.nextInt();
				slot = new Slot();
				System.out.println("Enter booking starting time in HH:mm format:");
				sc.nextLine();
				slot.setSlotStartTime(sc.nextLine()); // slot class
				System.out.println("Enter booking ending time in HH:mm format: ");
				slot.setSlotEndTime(sc.nextLine());
				System.out.println("Enter number of days for which you want to search ");
				int days= sc.nextInt(); 
				System.out.println("Enter the suggestion start date in yyyy-mm-dd format: ");
				sc.nextLine();
				date= sc.nextLine(); 
				bookingService.suggestRooms(bid,fid,date,slot,capacity,days);
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
