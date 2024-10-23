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

import com.vehicletrackingsystem.domain.Vehicle;
import com.vehicletrackingsystem.dto.VehicleDTO;
import com.vehicletrackingsystem.dto.VehicleSearchDTO;
import com.vehicletrackingsystem.dto.VehiclePageDTO;
import com.vehicletrackingsystem.service.VehicleService;
import com.vehicletrackingsystem.dto.common.RequestDTO;
import com.vehicletrackingsystem.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/vehicle")
@RestController
public class VehicleController {

	private final static Logger logger = LoggerFactory.getLogger(VehicleController.class);

	@Autowired
	VehicleService vehicleService;



	//@AllowSystem(AuthScopes.READ)
	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Vehicle> getAll() {

		List<Vehicle> vehicles = vehicleService.findAll();
		
		return vehicles;	
	}

	//@ReadAccess
	@GetMapping(value = "/{vehicleId}")
	@ResponseBody
	public VehicleDTO getVehicle(@PathVariable Integer vehicleId) {
		
		return (vehicleService.getVehicleDTOById(vehicleId));
	}

 	//@WriteAccess
 	@RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
	public ResponseEntity<?> addVehicle(@RequestBody VehicleDTO vehicleDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = vehicleService.addVehicle(vehicleDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}
		
		return result.asResponseEntity();
	}

	//@ReadAccess
	@GetMapping("/vehicles")
	public ResponseEntity<VehiclePageDTO> getVehicles(VehicleSearchDTO vehicleSearchDTO) {
 
		return vehicleService.getVehicles(vehicleSearchDTO);
	}	

 	//@WriteAccess
	@RequestMapping(value = "/updateVehicle", method = RequestMethod.POST)
	public ResponseEntity<?> updateVehicle(@RequestBody VehicleDTO vehicleDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = vehicleService.updateVehicle(vehicleDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
