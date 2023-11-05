package com.confRoom.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;

import com.confRoom.model.*;
import com.confRoom.model.ConfRoom.slotComparator;



public class BookingRepository implements IBookingRepository{
	
	public class bookingComparator implements Comparator<Booking>{

		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		java.util.Date date1;
		java.util.Date date2;
		public int compare(Booking b1, Booking b2) {
			
			Slot s1= b1.getSlot();
			Slot s2= b2.getSlot();
			
			try {
				date1 =  parser.parse(s1.getSlotStartTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				date2 = parser.parse(s2.getSlotStartTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return (int) (date1.getTime() - date2.getTime());
		}
	}

	
	public Map<Integer,Booking> Bookings=new HashMap<Integer,Booking>();
	static public UserRepository userRepo= UserRepository.getInstance();
	static public BuildingRepository buildingRepo= BuildingRepository.getInstance();

	
	
	private static BookingRepository BookingRepository_instance = null;
	
		private BookingRepository() {
			Bookings=new HashMap<Integer,Booking>();
		}
	
	 public static synchronized BookingRepository getInstance() 
	    { 
	        if (BookingRepository_instance == null) 
	        	BookingRepository_instance = new BookingRepository(); 
	  
	        return BookingRepository_instance; 
	    } 
	 
	 public Booking checkBookingPresence(int bookingId) {
			
			Booking booking= Bookings.get(bookingId);
			if(booking==null)
				System.out.println("The mentioned booking dosen't exists");
			return booking;
		}
	
	public void addBooking(Booking booking) { 
		ConfRoom confRoom = booking.getConfRoom();
		User user = userRepo.Users.get(booking.getUserId());
		
		Bookings.put(booking.getBookingId(), booking);
		
		//put in service layer
		user.setBookings(booking);					// ****** don't store booking in user ******  instead filter from booking 

		confRoom.setBooking(booking);

	}
	
	public void deleteBooking(Booking booking) { 
		
		Bookings.remove(booking.getBookingId());
		User user = userRepo.checkUserPresence(booking.getUserId());
		user.unsetBookings(booking.getBookingId());

		//put in service layer
		ConfRoom confRoom=booking.getConfRoom();
		confRoom.unsetBooking(booking);
		
	}
	
	public TreeSet<Booking> getBookings(int bid,int fid,int cid,String date)
	{
		TreeSet<Booking>bookings = new TreeSet<Booking>(new bookingComparator());
		Iterator<Entry<Integer, Booking>> hmIterator = this.Bookings.entrySet().iterator();
		
		while (hmIterator.hasNext()) {
			Entry<Integer, Booking> mapElement = hmIterator.next();
			Booking booking = mapElement.getValue();
			ConfRoom confRoom = booking.getConfRoom();

			if(confRoom.getConfRoomId()==cid && confRoom.getBuildingId()==bid && confRoom.getFloorId()==fid && booking.getDate().equals(date))
				bookings.add(booking);
			//System.out.println(booking);
		}
		
		
		return bookings;
	}
	
	public Map<Integer,Booking> listBookingsOfUser(int userId) {
		
		User user = userRepo.checkUserPresence(userId);
		
		if(user==null) {
			return null;
		}
		
		Map<Integer,Booking> bookings=user.getBookings();
		
		 
		 return bookings;
	}
	
	
	
}
