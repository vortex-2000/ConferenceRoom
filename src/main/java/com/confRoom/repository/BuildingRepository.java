package com.confRoom.repository;

import com.confRoom.model.*;
import java.util.*;

public class BuildingRepository implements IBuildingRepository{
	
	public Map<Integer,Building> Buildings;
	
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
	 
	 	public Building checkBuildingPresence(int buildingId) {
			Building building=Buildings.get(buildingId);		
			if(building==null)
				System.out.println("The mentioned building dosen't exists");
			return building;
		}
		
		

	
	public int addBuilding(String name) {
		Building building= new Building(name);
		Buildings.put(building.getBuildingId(), building);
		return building.getBuildingId();
		
	}
	//////////////////// SEPARATE FOR FLOOR AND CONFROOM

	
}
