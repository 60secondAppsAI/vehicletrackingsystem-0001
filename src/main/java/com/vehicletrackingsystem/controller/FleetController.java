package com.vehicletrackingsystem.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.vehicletrackingsystem.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.vehicletrackingsystem.domain.Fleet;
import com.vehicletrackingsystem.dto.FleetDTO;
import com.vehicletrackingsystem.dto.FleetSearchDTO;
import com.vehicletrackingsystem.dto.FleetPageDTO;
import com.vehicletrackingsystem.service.FleetService;
import com.vehicletrackingsystem.dto.common.RequestDTO;
import com.vehicletrackingsystem.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/fleet")
@RestController
public class FleetController {

	private final static Logger logger = LoggerFactory.getLogger(FleetController.class);

	@Autowired
	FleetService fleetService;



	//@AllowSystem(AuthScopes.READ)
	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Fleet> getAll() {

		List<Fleet> fleets = fleetService.findAll();
		
		return fleets;	
	}

	//@ReadAccess
	@GetMapping(value = "/{fleetId}")
	@ResponseBody
	public FleetDTO getFleet(@PathVariable Integer fleetId) {
		
		return (fleetService.getFleetDTOById(fleetId));
	}

 	//@WriteAccess
 	@RequestMapping(value = "/addFleet", method = RequestMethod.POST)
	public ResponseEntity<?> addFleet(@RequestBody FleetDTO fleetDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = fleetService.addFleet(fleetDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}
		
		return result.asResponseEntity();
	}

	//@ReadAccess
	@GetMapping("/fleets")
	public ResponseEntity<FleetPageDTO> getFleets(FleetSearchDTO fleetSearchDTO) {
 
		return fleetService.getFleets(fleetSearchDTO);
	}	

 	//@WriteAccess
	@RequestMapping(value = "/updateFleet", method = RequestMethod.POST)
	public ResponseEntity<?> updateFleet(@RequestBody FleetDTO fleetDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = fleetService.updateFleet(fleetDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
