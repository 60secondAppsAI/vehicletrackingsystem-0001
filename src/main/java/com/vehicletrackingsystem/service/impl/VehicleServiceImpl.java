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
import com.vehicletrackingsystem.dao.VehicleDAO;
import com.vehicletrackingsystem.domain.Vehicle;
import com.vehicletrackingsystem.dto.VehicleDTO;
import com.vehicletrackingsystem.dto.VehicleSearchDTO;
import com.vehicletrackingsystem.dto.VehiclePageDTO;
import com.vehicletrackingsystem.dto.VehicleConvertCriteriaDTO;
import com.vehicletrackingsystem.dto.common.RequestDTO;
import com.vehicletrackingsystem.dto.common.ResultDTO;
import com.vehicletrackingsystem.service.VehicleService;
import com.vehicletrackingsystem.util.ControllerUtils;





@Service
public class VehicleServiceImpl extends GenericServiceImpl<Vehicle, Integer> implements VehicleService {

    private final static Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

	@Autowired
	VehicleDAO vehicleDao;

	


	@Override
	public GenericDAO<Vehicle, Integer> getDAO() {
		return (GenericDAO<Vehicle, Integer>) vehicleDao;
	}
	
	public List<Vehicle> findAll () {
		List<Vehicle> vehicles = vehicleDao.findAll();
		
		return vehicles;	
		
	}

	public ResultDTO addVehicle(VehicleDTO vehicleDTO, RequestDTO requestDTO) {

		Vehicle vehicle = new Vehicle();

		vehicle.setVehicleId(vehicleDTO.getVehicleId());


		vehicle.setLicensePlate(vehicleDTO.getLicensePlate());


		vehicle.setModel(vehicleDTO.getModel());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		vehicle = vehicleDao.save(vehicle);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Vehicle> getAllVehicles(Pageable pageable) {
		return vehicleDao.findAll(pageable);
	}

	public Page<Vehicle> getAllVehicles(Specification<Vehicle> spec, Pageable pageable) {
		return vehicleDao.findAll(spec, pageable);
	}

	public ResponseEntity<VehiclePageDTO> getVehicles(VehicleSearchDTO vehicleSearchDTO) {
	
			Integer vehicleId = vehicleSearchDTO.getVehicleId(); 
 			String licensePlate = vehicleSearchDTO.getLicensePlate(); 
 			String model = vehicleSearchDTO.getModel(); 
 			String sortBy = vehicleSearchDTO.getSortBy();
			String sortOrder = vehicleSearchDTO.getSortOrder();
			String searchQuery = vehicleSearchDTO.getSearchQuery();
			Integer page = vehicleSearchDTO.getPage();
			Integer size = vehicleSearchDTO.getSize();

	        Specification<Vehicle> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, vehicleId, "vehicleId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, licensePlate, "licensePlate"); 
			
			spec = ControllerUtils.andIfNecessary(spec, model, "model"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("licensePlate")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("model")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Vehicle> vehicles = this.getAllVehicles(spec, pageable);
		
		//System.out.println(String.valueOf(vehicles.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(vehicles.getTotalPages()));
		
		List<Vehicle> vehiclesList = vehicles.getContent();
		
		VehicleConvertCriteriaDTO convertCriteria = new VehicleConvertCriteriaDTO();
		List<VehicleDTO> vehicleDTOs = this.convertVehiclesToVehicleDTOs(vehiclesList,convertCriteria);
		
		VehiclePageDTO vehiclePageDTO = new VehiclePageDTO();
		vehiclePageDTO.setVehicles(vehicleDTOs);
		vehiclePageDTO.setTotalElements(vehicles.getTotalElements());
		return ResponseEntity.ok(vehiclePageDTO);
	}

	public List<VehicleDTO> convertVehiclesToVehicleDTOs(List<Vehicle> vehicles, VehicleConvertCriteriaDTO convertCriteria) {
		
		List<VehicleDTO> vehicleDTOs = new ArrayList<VehicleDTO>();
		
		for (Vehicle vehicle : vehicles) {
			vehicleDTOs.add(convertVehicleToVehicleDTO(vehicle,convertCriteria));
		}
		
		return vehicleDTOs;

	}
	
	public VehicleDTO convertVehicleToVehicleDTO(Vehicle vehicle, VehicleConvertCriteriaDTO convertCriteria) {
		
		VehicleDTO vehicleDTO = new VehicleDTO();
		
		vehicleDTO.setVehicleId(vehicle.getVehicleId());

	
		vehicleDTO.setLicensePlate(vehicle.getLicensePlate());

	
		vehicleDTO.setModel(vehicle.getModel());

	

		
		return vehicleDTO;
	}

	public ResultDTO updateVehicle(VehicleDTO vehicleDTO, RequestDTO requestDTO) {
		
		Vehicle vehicle = vehicleDao.getById(vehicleDTO.getVehicleId());

		vehicle.setVehicleId(ControllerUtils.setValue(vehicle.getVehicleId(), vehicleDTO.getVehicleId()));

		vehicle.setLicensePlate(ControllerUtils.setValue(vehicle.getLicensePlate(), vehicleDTO.getLicensePlate()));

		vehicle.setModel(ControllerUtils.setValue(vehicle.getModel(), vehicleDTO.getModel()));



        vehicle = vehicleDao.save(vehicle);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public VehicleDTO getVehicleDTOById(Integer vehicleId) {
	
		Vehicle vehicle = vehicleDao.getById(vehicleId);
			
		
		VehicleConvertCriteriaDTO convertCriteria = new VehicleConvertCriteriaDTO();
		return(this.convertVehicleToVehicleDTO(vehicle,convertCriteria));
	}







}
