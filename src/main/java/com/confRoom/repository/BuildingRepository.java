package com.confRoom.repository;

import com.confRoom.model.*;
import java.util.*;

public class BuildingRepository implements IBuildingRepository{
	
	public Map<Integer,Building> Buildings;   // variable name is small           ///private and getter
	
	private static BuildingRepository BuildingRepository_instance = null;
	
	private BuildingRepository() {
		Buildings=new HashMap<Integer,Building>();
	}
	
	 public static synchronized BuildingRepository getInstance() 
	    { 
	        if (BuildingRepository_instance == null) 
	        	BuildingRepository_instance = new BuildingRepository(); 
	  
	        return BuildingRepository_instance; 
	    }
	 
	 	
	 	
		public Building getBuildingById(int id)
		{
			return Buildings.get(id);
		}
		
		//getbuildingbyId check presence in service 

	
	public Building addBuilding(String name) {
		Building building= new Building(name); // building obj as param
		Buildings.put(building.getBuildingId(), building);
		return building;
		
	}
	
	

	
}
