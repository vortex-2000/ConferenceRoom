import java.util.Scanner;

import com.confRoom.repository.BuildingRepository;
import com.confRoom.service.BookingService;
import com.confRoom.service.BuildingService;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BuildingService buildingService = new BuildingService();
		Scanner sc= new Scanner(System.in); //System.in is a standard input stream.
		
		int action=0;
		
		while(action!=-1) {
			System.out.println("Enter the action you want to perform: ");
			action= sc.nextInt();
			
			switch(action){
			
			case 1:
				System.out.println("Enter a building name: ");
				sc.nextLine();
				String bstr= sc.nextLine(); 
				buildingService.ConstructBuilding(bstr);
				break;
				
			case 2:
				System.out.println("Enter a building id: ");
				int bid= sc.nextInt();
				System.out.println("Enter a floor name: ");
				sc.nextLine();
				String fstr= sc.nextLine(); 
				buildingService.ConstructFloor(bid,fstr);
				break;
				
			case 3:
				System.out.println("Enter a building id: ");
				sc= new Scanner(System.in);
				bid= sc.nextInt();
				System.out.println("Enter a floor id: ");
				int fid= sc.nextInt(); 
				System.out.println("Enter a capacity: ");
				int capacity= sc.nextInt(); 
				System.out.println("Enter a conference room name: ");
				sc.nextLine();
				String cstr= sc.nextLine(); 
				buildingService.ConstructConfRoom(bid,fid,capacity,cstr);
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
