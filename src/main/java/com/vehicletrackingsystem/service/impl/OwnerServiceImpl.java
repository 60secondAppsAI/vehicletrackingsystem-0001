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
import com.vehicletrackingsystem.dao.OwnerDAO;
import com.vehicletrackingsystem.domain.Owner;
import com.vehicletrackingsystem.dto.OwnerDTO;
import com.vehicletrackingsystem.dto.OwnerSearchDTO;
import com.vehicletrackingsystem.dto.OwnerPageDTO;
import com.vehicletrackingsystem.dto.OwnerConvertCriteriaDTO;
import com.vehicletrackingsystem.dto.common.RequestDTO;
import com.vehicletrackingsystem.dto.common.ResultDTO;
import com.vehicletrackingsystem.service.OwnerService;
import com.vehicletrackingsystem.util.ControllerUtils;





@Service
public class OwnerServiceImpl extends GenericServiceImpl<Owner, Integer> implements OwnerService {

    private final static Logger logger = LoggerFactory.getLogger(OwnerServiceImpl.class);

	@Autowired
	OwnerDAO ownerDao;

	


	@Override
	public GenericDAO<Owner, Integer> getDAO() {
		return (GenericDAO<Owner, Integer>) ownerDao;
	}
	
	public List<Owner> findAll () {
		List<Owner> owners = ownerDao.findAll();
		
		return owners;	
		
	}

	public ResultDTO addOwner(OwnerDTO ownerDTO, RequestDTO requestDTO) {

		Owner owner = new Owner();

		owner.setOwnerId(ownerDTO.getOwnerId());


		owner.setName(ownerDTO.getName());


		owner.setContactNumber(ownerDTO.getContactNumber());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		owner = ownerDao.save(owner);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Owner> getAllOwners(Pageable pageable) {
		return ownerDao.findAll(pageable);
	}

	public Page<Owner> getAllOwners(Specification<Owner> spec, Pageable pageable) {
		return ownerDao.findAll(spec, pageable);
	}

	public ResponseEntity<OwnerPageDTO> getOwners(OwnerSearchDTO ownerSearchDTO) {
	
			Integer ownerId = ownerSearchDTO.getOwnerId(); 
 			String name = ownerSearchDTO.getName(); 
 			String contactNumber = ownerSearchDTO.getContactNumber(); 
 			String sortBy = ownerSearchDTO.getSortBy();
			String sortOrder = ownerSearchDTO.getSortOrder();
			String searchQuery = ownerSearchDTO.getSearchQuery();
			Integer page = ownerSearchDTO.getPage();
			Integer size = ownerSearchDTO.getSize();

	        Specification<Owner> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, ownerId, "ownerId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, contactNumber, "contactNumber"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("contactNumber")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Owner> owners = this.getAllOwners(spec, pageable);
		
		//System.out.println(String.valueOf(owners.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(owners.getTotalPages()));
		
		List<Owner> ownersList = owners.getContent();
		
		OwnerConvertCriteriaDTO convertCriteria = new OwnerConvertCriteriaDTO();
		List<OwnerDTO> ownerDTOs = this.convertOwnersToOwnerDTOs(ownersList,convertCriteria);
		
		OwnerPageDTO ownerPageDTO = new OwnerPageDTO();
		ownerPageDTO.setOwners(ownerDTOs);
		ownerPageDTO.setTotalElements(owners.getTotalElements());
		return ResponseEntity.ok(ownerPageDTO);
	}

	public List<OwnerDTO> convertOwnersToOwnerDTOs(List<Owner> owners, OwnerConvertCriteriaDTO convertCriteria) {
		
		List<OwnerDTO> ownerDTOs = new ArrayList<OwnerDTO>();
		
		for (Owner owner : owners) {
			ownerDTOs.add(convertOwnerToOwnerDTO(owner,convertCriteria));
		}
		
		return ownerDTOs;

	}
	
	public OwnerDTO convertOwnerToOwnerDTO(Owner owner, OwnerConvertCriteriaDTO convertCriteria) {
		
		OwnerDTO ownerDTO = new OwnerDTO();
		
		ownerDTO.setOwnerId(owner.getOwnerId());

	
		ownerDTO.setName(owner.getName());

	
		ownerDTO.setContactNumber(owner.getContactNumber());

	

		
		return ownerDTO;
	}

	public ResultDTO updateOwner(OwnerDTO ownerDTO, RequestDTO requestDTO) {
		
		Owner owner = ownerDao.getById(ownerDTO.getOwnerId());

		owner.setOwnerId(ControllerUtils.setValue(owner.getOwnerId(), ownerDTO.getOwnerId()));

		owner.setName(ControllerUtils.setValue(owner.getName(), ownerDTO.getName()));

		owner.setContactNumber(ControllerUtils.setValue(owner.getContactNumber(), ownerDTO.getContactNumber()));



        owner = ownerDao.save(owner);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public OwnerDTO getOwnerDTOById(Integer ownerId) {
	
		Owner owner = ownerDao.getById(ownerId);
			
		
		OwnerConvertCriteriaDTO convertCriteria = new OwnerConvertCriteriaDTO();
		return(this.convertOwnerToOwnerDTO(owner,convertCriteria));
	}







}
