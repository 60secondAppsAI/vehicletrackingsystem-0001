package com.vehicletrackingsystem.dao;

import java.util.List;

import com.vehicletrackingsystem.dao.GenericDAO;
import com.vehicletrackingsystem.domain.Fleet;





public interface FleetDAO extends GenericDAO<Fleet, Integer> {
  
	List<Fleet> findAll();
	






}


