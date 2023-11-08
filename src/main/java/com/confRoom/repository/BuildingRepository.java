package com.confRoom.repository;

import com.confRoom.model.*;
import java.util.*;

public class BuildingRepository implements IBuildingRepository{
	
	private Map<Integer,Building> buildings;   
	
	private static BuildingRepository BuildingRepository_instance = null;
	
	private BuildingRepository() {
		buildings=new HashMap<Integer,Building>();
	}
	
	 public static synchronized BuildingRepository getInstance() 
	    { 
	        if (BuildingRepository_instance == null) 
	        	BuildingRepository_instance = new BuildingRepository(); 
	  
	        return BuildingRepository_instance; 
	    }
	 
	 	
	 	
	public Building getBuildingById(int id)
	{
		return buildings.get(id);
	}
		

	public Building addBuilding(String name) {
		Building building= new Building(name); 
		buildings.put(building.getBuildingId(), building);
		return building;
		
	}
	
	public Map<Integer,Building> getBuildings(){
		return this.buildings;
	}	
}
