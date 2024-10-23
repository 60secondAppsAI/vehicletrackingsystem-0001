package com.vehicletrackingsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.vehicletrackingsystem.domain.Vehicle;
import com.vehicletrackingsystem.dto.VehicleDTO;
import com.vehicletrackingsystem.dto.VehicleSearchDTO;
import com.vehicletrackingsystem.dto.VehiclePageDTO;
import com.vehicletrackingsystem.dto.VehicleConvertCriteriaDTO;
import com.vehicletrackingsystem.service.GenericService;
import com.vehicletrackingsystem.dto.common.RequestDTO;
import com.vehicletrackingsystem.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface VehicleService extends GenericService<Vehicle, Integer> {

	List<Vehicle> findAll();

	ResultDTO addVehicle(VehicleDTO vehicleDTO, RequestDTO requestDTO);

	ResultDTO updateVehicle(VehicleDTO vehicleDTO, RequestDTO requestDTO);

    Page<Vehicle> getAllVehicles(Pageable pageable);

    Page<Vehicle> getAllVehicles(Specification<Vehicle> spec, Pageable pageable);

	ResponseEntity<VehiclePageDTO> getVehicles(VehicleSearchDTO vehicleSearchDTO);
	
	List<VehicleDTO> convertVehiclesToVehicleDTOs(List<Vehicle> vehicles, VehicleConvertCriteriaDTO convertCriteria);

	VehicleDTO getVehicleDTOById(Integer vehicleId);







}





