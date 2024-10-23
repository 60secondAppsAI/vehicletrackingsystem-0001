package com.vehicletrackingsystem.dao;

import java.util.List;

import com.vehicletrackingsystem.dao.GenericDAO;
import com.vehicletrackingsystem.domain.Owner;





public interface OwnerDAO extends GenericDAO<Owner, Integer> {
  
	List<Owner> findAll();
	






}


