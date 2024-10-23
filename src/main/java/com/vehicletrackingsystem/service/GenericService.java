package com.vehicletrackingsystem.service;

import com.vehicletrackingsystem.dao.GenericDAO;

public interface GenericService<T, ID> {

    abstract GenericDAO<T, ID> getDAO();

    T getById(Integer id) ;

}