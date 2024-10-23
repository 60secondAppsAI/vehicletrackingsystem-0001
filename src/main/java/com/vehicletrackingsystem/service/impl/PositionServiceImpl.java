package com.vehicletrackingsystem.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.vehicletrackingsystem.dao.GenericDAO;
import com.vehicletrackingsystem.service.GenericService;
import com.vehicletrackingsystem.service.impl.GenericServiceImpl;
import com.vehicletrackingsystem.dao.PositionDAO;
import com.vehicletrackingsystem.domain.Position;
import com.vehicletrackingsystem.dto.PositionDTO;
import com.vehicletrackingsystem.dto.PositionSearchDTO;
import com.vehicletrackingsystem.dto.PositionPageDTO;
import com.vehicletrackingsystem.dto.PositionConvertCriteriaDTO;
import com.vehicletrackingsystem.dto.common.RequestDTO;
import com.vehicletrackingsystem.dto.common.ResultDTO;
import com.vehicletrackingsystem.service.PositionService;
import com.vehicletrackingsystem.util.ControllerUtils;





@Service
public class PositionServiceImpl extends GenericServiceImpl<Position, Integer> implements PositionService {

    private final static Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);

	@Autowired
	PositionDAO positionDao;

	


	@Override
	public GenericDAO<Position, Integer> getDAO() {
		return (GenericDAO<Position, Integer>) positionDao;
	}
	
	public List<Position> findAll () {
		List<Position> positions = positionDao.findAll();
		
		return positions;	
		
	}

	public ResultDTO addPosition(PositionDTO positionDTO, RequestDTO requestDTO) {

		Position position = new Position();

		position.setPositionId(positionDTO.getPositionId());


		position.setLatitude(positionDTO.getLatitude());


		position.setLongitude(positionDTO.getLongitude());


		position.setTimestamp(positionDTO.getTimestamp());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		position = positionDao.save(position);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Position> getAllPositions(Pageable pageable) {
		return positionDao.findAll(pageable);
	}

	public Page<Position> getAllPositions(Specification<Position> spec, Pageable pageable) {
		return positionDao.findAll(spec, pageable);
	}

	public ResponseEntity<PositionPageDTO> getPositions(PositionSearchDTO positionSearchDTO) {
	
			Integer positionId = positionSearchDTO.getPositionId(); 
    			String sortBy = positionSearchDTO.getSortBy();
			String sortOrder = positionSearchDTO.getSortOrder();
			String searchQuery = positionSearchDTO.getSearchQuery();
			Integer page = positionSearchDTO.getPage();
			Integer size = positionSearchDTO.getSize();

	        Specification<Position> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, positionId, "positionId"); 
			
			
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Position> positions = this.getAllPositions(spec, pageable);
		
		//System.out.println(String.valueOf(positions.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(positions.getTotalPages()));
		
		List<Position> positionsList = positions.getContent();
		
		PositionConvertCriteriaDTO convertCriteria = new PositionConvertCriteriaDTO();
		List<PositionDTO> positionDTOs = this.convertPositionsToPositionDTOs(positionsList,convertCriteria);
		
		PositionPageDTO positionPageDTO = new PositionPageDTO();
		positionPageDTO.setPositions(positionDTOs);
		positionPageDTO.setTotalElements(positions.getTotalElements());
		return ResponseEntity.ok(positionPageDTO);
	}

	public List<PositionDTO> convertPositionsToPositionDTOs(List<Position> positions, PositionConvertCriteriaDTO convertCriteria) {
		
		List<PositionDTO> positionDTOs = new ArrayList<PositionDTO>();
		
		for (Position position : positions) {
			positionDTOs.add(convertPositionToPositionDTO(position,convertCriteria));
		}
		
		return positionDTOs;

	}
	
	public PositionDTO convertPositionToPositionDTO(Position position, PositionConvertCriteriaDTO convertCriteria) {
		
		PositionDTO positionDTO = new PositionDTO();
		
		positionDTO.setPositionId(position.getPositionId());

	
		positionDTO.setLatitude(position.getLatitude());

	
		positionDTO.setLongitude(position.getLongitude());

	
		positionDTO.setTimestamp(position.getTimestamp());

	

		
		return positionDTO;
	}

	public ResultDTO updatePosition(PositionDTO positionDTO, RequestDTO requestDTO) {
		
		Position position = positionDao.getById(positionDTO.getPositionId());

		position.setPositionId(ControllerUtils.setValue(position.getPositionId(), positionDTO.getPositionId()));

		position.setLatitude(ControllerUtils.setValue(position.getLatitude(), positionDTO.getLatitude()));

		position.setLongitude(ControllerUtils.setValue(position.getLongitude(), positionDTO.getLongitude()));

		position.setTimestamp(ControllerUtils.setValue(position.getTimestamp(), positionDTO.getTimestamp()));



        position = positionDao.save(position);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PositionDTO getPositionDTOById(Integer positionId) {
	
		Position position = positionDao.getById(positionId);
			
		
		PositionConvertCriteriaDTO convertCriteria = new PositionConvertCriteriaDTO();
		return(this.convertPositionToPositionDTO(position,convertCriteria));
	}







}
