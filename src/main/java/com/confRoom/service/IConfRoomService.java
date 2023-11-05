package com.confRoom.service;

import com.confRoom.model.ConfRoom;

public interface IConfRoomService {

	public ConfRoom constructConfRoom(int id_b,int id_f,int capacity,String name);
	
}
