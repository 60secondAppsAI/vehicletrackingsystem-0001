package com.vehicletrackingsystem.dao;

import java.util.List;

import com.vehicletrackingsystem.dao.GenericDAO;
import com.vehicletrackingsystem.domain.Position;





public interface PositionDAO extends GenericDAO<Position, Integer> {
  
	List<Position> findAll();
	






}


