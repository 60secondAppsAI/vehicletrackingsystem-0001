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

import com.vehicletrackingsystem.domain.Position;
import com.vehicletrackingsystem.dto.PositionDTO;
import com.vehicletrackingsystem.dto.PositionSearchDTO;
import com.vehicletrackingsystem.dto.PositionPageDTO;
import com.vehicletrackingsystem.service.PositionService;
import com.vehicletrackingsystem.dto.common.RequestDTO;
import com.vehicletrackingsystem.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/position")
@RestController
public class PositionController {

	private final static Logger logger = LoggerFactory.getLogger(PositionController.class);

	@Autowired
	PositionService positionService;



	//@AllowSystem(AuthScopes.READ)
	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Position> getAll() {

		List<Position> positions = positionService.findAll();
		
		return positions;	
	}

	//@ReadAccess
	@GetMapping(value = "/{positionId}")
	@ResponseBody
	public PositionDTO getPosition(@PathVariable Integer positionId) {
		
		return (positionService.getPositionDTOById(positionId));
	}

 	//@WriteAccess
 	@RequestMapping(value = "/addPosition", method = RequestMethod.POST)
	public ResponseEntity<?> addPosition(@RequestBody PositionDTO positionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = positionService.addPosition(positionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}
		
		return result.asResponseEntity();
	}

	//@ReadAccess
	@GetMapping("/positions")
	public ResponseEntity<PositionPageDTO> getPositions(PositionSearchDTO positionSearchDTO) {
 
		return positionService.getPositions(positionSearchDTO);
	}	

 	//@WriteAccess
	@RequestMapping(value = "/updatePosition", method = RequestMethod.POST)
	public ResponseEntity<?> updatePosition(@RequestBody PositionDTO positionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = positionService.updatePosition(positionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
