package com.vehicletrackingsystem.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PositionDTO {

	private Integer positionId;

	private double latitude;

	private double longitude;

	private DateTime timestamp;






}
