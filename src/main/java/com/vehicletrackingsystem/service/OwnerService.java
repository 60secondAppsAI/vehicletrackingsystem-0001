package com.vehicletrackingsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.vehicletrackingsystem.domain.Owner;
import com.vehicletrackingsystem.dto.OwnerDTO;
import com.vehicletrackingsystem.dto.OwnerSearchDTO;
import com.vehicletrackingsystem.dto.OwnerPageDTO;
import com.vehicletrackingsystem.dto.OwnerConvertCriteriaDTO;
import com.vehicletrackingsystem.service.GenericService;
import com.vehicletrackingsystem.dto.common.RequestDTO;
import com.vehicletrackingsystem.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface OwnerService extends GenericService<Owner, Integer> {

	List<Owner> findAll();

	ResultDTO addOwner(OwnerDTO ownerDTO, RequestDTO requestDTO);

	ResultDTO updateOwner(OwnerDTO ownerDTO, RequestDTO requestDTO);

    Page<Owner> getAllOwners(Pageable pageable);

    Page<Owner> getAllOwners(Specification<Owner> spec, Pageable pageable);

	ResponseEntity<OwnerPageDTO> getOwners(OwnerSearchDTO ownerSearchDTO);
	
	List<OwnerDTO> convertOwnersToOwnerDTOs(List<Owner> owners, OwnerConvertCriteriaDTO convertCriteria);

	OwnerDTO getOwnerDTOById(Integer ownerId);







}





