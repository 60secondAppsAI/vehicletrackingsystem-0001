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
import com.vehicletrackingsystem.dao.FleetDAO;
import com.vehicletrackingsystem.domain.Fleet;
import com.vehicletrackingsystem.dto.FleetDTO;
import com.vehicletrackingsystem.dto.FleetSearchDTO;
import com.vehicletrackingsystem.dto.FleetPageDTO;
import com.vehicletrackingsystem.dto.FleetConvertCriteriaDTO;
import com.vehicletrackingsystem.dto.common.RequestDTO;
import com.vehicletrackingsystem.dto.common.ResultDTO;
import com.vehicletrackingsystem.service.FleetService;
import com.vehicletrackingsystem.util.ControllerUtils;





@Service
public class FleetServiceImpl extends GenericServiceImpl<Fleet, Integer> implements FleetService {

    private final static Logger logger = LoggerFactory.getLogger(FleetServiceImpl.class);

	@Autowired
	FleetDAO fleetDao;

	


	@Override
	public GenericDAO<Fleet, Integer> getDAO() {
		return (GenericDAO<Fleet, Integer>) fleetDao;
	}
	
	public List<Fleet> findAll () {
		List<Fleet> fleets = fleetDao.findAll();
		
		return fleets;	
		
	}

	public ResultDTO addFleet(FleetDTO fleetDTO, RequestDTO requestDTO) {

		Fleet fleet = new Fleet();

		fleet.setFleetId(fleetDTO.getFleetId());


		fleet.setName(fleetDTO.getName());


		fleet.setDescription(fleetDTO.getDescription());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		fleet = fleetDao.save(fleet);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Fleet> getAllFleets(Pageable pageable) {
		return fleetDao.findAll(pageable);
	}

	public Page<Fleet> getAllFleets(Specification<Fleet> spec, Pageable pageable) {
		return fleetDao.findAll(spec, pageable);
	}

	public ResponseEntity<FleetPageDTO> getFleets(FleetSearchDTO fleetSearchDTO) {
	
			Integer fleetId = fleetSearchDTO.getFleetId(); 
 			String name = fleetSearchDTO.getName(); 
 			String description = fleetSearchDTO.getDescription(); 
 			String sortBy = fleetSearchDTO.getSortBy();
			String sortOrder = fleetSearchDTO.getSortOrder();
			String searchQuery = fleetSearchDTO.getSearchQuery();
			Integer page = fleetSearchDTO.getPage();
			Integer size = fleetSearchDTO.getSize();

	        Specification<Fleet> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, fleetId, "fleetId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Fleet> fleets = this.getAllFleets(spec, pageable);
		
		//System.out.println(String.valueOf(fleets.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(fleets.getTotalPages()));
		
		List<Fleet> fleetsList = fleets.getContent();
		
		FleetConvertCriteriaDTO convertCriteria = new FleetConvertCriteriaDTO();
		List<FleetDTO> fleetDTOs = this.convertFleetsToFleetDTOs(fleetsList,convertCriteria);
		
		FleetPageDTO fleetPageDTO = new FleetPageDTO();
		fleetPageDTO.setFleets(fleetDTOs);
		fleetPageDTO.setTotalElements(fleets.getTotalElements());
		return ResponseEntity.ok(fleetPageDTO);
	}

	public List<FleetDTO> convertFleetsToFleetDTOs(List<Fleet> fleets, FleetConvertCriteriaDTO convertCriteria) {
		
		List<FleetDTO> fleetDTOs = new ArrayList<FleetDTO>();
		
		for (Fleet fleet : fleets) {
			fleetDTOs.add(convertFleetToFleetDTO(fleet,convertCriteria));
		}
		
		return fleetDTOs;

	}
	
	public FleetDTO convertFleetToFleetDTO(Fleet fleet, FleetConvertCriteriaDTO convertCriteria) {
		
		FleetDTO fleetDTO = new FleetDTO();
		
		fleetDTO.setFleetId(fleet.getFleetId());

	
		fleetDTO.setName(fleet.getName());

	
		fleetDTO.setDescription(fleet.getDescription());

	

		
		return fleetDTO;
	}

	public ResultDTO updateFleet(FleetDTO fleetDTO, RequestDTO requestDTO) {
		
		Fleet fleet = fleetDao.getById(fleetDTO.getFleetId());

		fleet.setFleetId(ControllerUtils.setValue(fleet.getFleetId(), fleetDTO.getFleetId()));

		fleet.setName(ControllerUtils.setValue(fleet.getName(), fleetDTO.getName()));

		fleet.setDescription(ControllerUtils.setValue(fleet.getDescription(), fleetDTO.getDescription()));



        fleet = fleetDao.save(fleet);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public FleetDTO getFleetDTOById(Integer fleetId) {
	
		Fleet fleet = fleetDao.getById(fleetId);
			
		
		FleetConvertCriteriaDTO convertCriteria = new FleetConvertCriteriaDTO();
		return(this.convertFleetToFleetDTO(fleet,convertCriteria));
	}







}
