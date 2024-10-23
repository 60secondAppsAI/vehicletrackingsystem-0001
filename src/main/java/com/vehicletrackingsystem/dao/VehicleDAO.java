package com.vehicletrackingsystem.dao;

import java.util.List;

import com.vehicletrackingsystem.dao.GenericDAO;
import com.vehicletrackingsystem.domain.Vehicle;





public interface VehicleDAO extends GenericDAO<Vehicle, Integer> {
  
	List<Vehicle> findAll();
	






}


