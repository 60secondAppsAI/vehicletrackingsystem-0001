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

import com.vehicletrackingsystem.domain.Owner;
import com.vehicletrackingsystem.dto.OwnerDTO;
import com.vehicletrackingsystem.dto.OwnerSearchDTO;
import com.vehicletrackingsystem.dto.OwnerPageDTO;
import com.vehicletrackingsystem.service.OwnerService;
import com.vehicletrackingsystem.dto.common.RequestDTO;
import com.vehicletrackingsystem.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/owner")
@RestController
public class OwnerController {

	private final static Logger logger = LoggerFactory.getLogger(OwnerController.class);

	@Autowired
	OwnerService ownerService;



	//@AllowSystem(AuthScopes.READ)
	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Owner> getAll() {

		List<Owner> owners = ownerService.findAll();
		
		return owners;	
	}

	//@ReadAccess
	@GetMapping(value = "/{ownerId}")
	@ResponseBody
	public OwnerDTO getOwner(@PathVariable Integer ownerId) {
		
		return (ownerService.getOwnerDTOById(ownerId));
	}

 	//@WriteAccess
 	@RequestMapping(value = "/addOwner", method = RequestMethod.POST)
	public ResponseEntity<?> addOwner(@RequestBody OwnerDTO ownerDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = ownerService.addOwner(ownerDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}
		
		return result.asResponseEntity();
	}

	//@ReadAccess
	@GetMapping("/owners")
	public ResponseEntity<OwnerPageDTO> getOwners(OwnerSearchDTO ownerSearchDTO) {
 
		return ownerService.getOwners(ownerSearchDTO);
	}	

 	//@WriteAccess
	@RequestMapping(value = "/updateOwner", method = RequestMethod.POST)
	public ResponseEntity<?> updateOwner(@RequestBody OwnerDTO ownerDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = ownerService.updateOwner(ownerDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
