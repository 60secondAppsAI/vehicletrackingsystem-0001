package com.vehicletrackingsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.vehicletrackingsystem.domain.Fleet;
import com.vehicletrackingsystem.dto.FleetDTO;
import com.vehicletrackingsystem.dto.FleetSearchDTO;
import com.vehicletrackingsystem.dto.FleetPageDTO;
import com.vehicletrackingsystem.dto.FleetConvertCriteriaDTO;
import com.vehicletrackingsystem.service.GenericService;
import com.vehicletrackingsystem.dto.common.RequestDTO;
import com.vehicletrackingsystem.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface FleetService extends GenericService<Fleet, Integer> {

	List<Fleet> findAll();

	ResultDTO addFleet(FleetDTO fleetDTO, RequestDTO requestDTO);

	ResultDTO updateFleet(FleetDTO fleetDTO, RequestDTO requestDTO);

    Page<Fleet> getAllFleets(Pageable pageable);

    Page<Fleet> getAllFleets(Specification<Fleet> spec, Pageable pageable);

	ResponseEntity<FleetPageDTO> getFleets(FleetSearchDTO fleetSearchDTO);
	
	List<FleetDTO> convertFleetsToFleetDTOs(List<Fleet> fleets, FleetConvertCriteriaDTO convertCriteria);

	FleetDTO getFleetDTOById(Integer fleetId);







}





