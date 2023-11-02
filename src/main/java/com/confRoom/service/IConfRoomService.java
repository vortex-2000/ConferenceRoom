package com.confRoom.service;

public interface IConfRoomService {

	public int constructConfRoom(int id_b,int id_f,int capacity,String name);
	public void listAllBookings(int buildingId, int floorId, int confRoomID, String date);
}
