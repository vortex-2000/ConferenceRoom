package com.confRoom.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
			
			LocalDate Dateb1 = LocalDate.parse(b1.getDate());
			LocalDate Dateb2 = LocalDate.parse(b2.getDate());
			
			System.out.println(Dateb1);
			System.out.println(Dateb2);
			System.out.println("***");
			
			if(Dateb1.equals(Dateb2)) {
				System.out.println(Dateb1);
				System.out.println(Dateb2);
				System.out.println("###");
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
			
			if(Dateb1.isBefore(Dateb2))
				return 1;
			return -1;
			
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
	 
	
	public void addBooking(Booking booking) { 
		ConfRoom confRoom = booking.getConfRoom();
		User user = userRepo.Users.get(booking.getUserId());
		
		Bookings.put(booking.getBookingId(), booking);
		
		confRoom.setBooking(booking);

	}
	
	public void deleteBooking(Booking booking) { 
		
		Bookings.remove(booking.getBookingId());
		User user = userRepo.getUserById(booking.getUserId());
		ConfRoom confRoom=booking.getConfRoom();
		confRoom.unsetBooking(booking);
		
	}
	
	public TreeSet<Booking> getBookingsByRoom(int bid,int fid,int cid,String date)
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
	
	public TreeSet<Booking> getBookingsByUser(int userId) {
	
		
		
		
		TreeSet<Booking>bookings = new TreeSet<Booking>(new bookingComparator());
		Iterator<Entry<Integer, Booking>> hmIterator = this.Bookings.entrySet().iterator();
		
		while (hmIterator.hasNext()) {
			Entry<Integer, Booking> mapElement = hmIterator.next();
			Booking booking = mapElement.getValue();
			
			if(booking.getUserId()==userId)
				bookings.add(booking);
			
		}
		
		 return bookings;
	}
	
	public Booking getBookingById(int bookingId) {
		
		return this.Bookings.get(bookingId);
	}
	
	
	
}
