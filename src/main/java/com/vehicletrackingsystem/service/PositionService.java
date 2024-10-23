package com.vehicletrackingsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.vehicletrackingsystem.domain.Position;
import com.vehicletrackingsystem.dto.PositionDTO;
import com.vehicletrackingsystem.dto.PositionSearchDTO;
import com.vehicletrackingsystem.dto.PositionPageDTO;
import com.vehicletrackingsystem.dto.PositionConvertCriteriaDTO;
import com.vehicletrackingsystem.service.GenericService;
import com.vehicletrackingsystem.dto.common.RequestDTO;
import com.vehicletrackingsystem.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PositionService extends GenericService<Position, Integer> {

	List<Position> findAll();

	ResultDTO addPosition(PositionDTO positionDTO, RequestDTO requestDTO);

	ResultDTO updatePosition(PositionDTO positionDTO, RequestDTO requestDTO);

    Page<Position> getAllPositions(Pageable pageable);

    Page<Position> getAllPositions(Specification<Position> spec, Pageable pageable);

	ResponseEntity<PositionPageDTO> getPositions(PositionSearchDTO positionSearchDTO);
	
	List<PositionDTO> convertPositionsToPositionDTOs(List<Position> positions, PositionConvertCriteriaDTO convertCriteria);

	PositionDTO getPositionDTOById(Integer positionId);







}





