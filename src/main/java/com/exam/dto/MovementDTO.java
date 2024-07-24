package com.exam.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class MovementDTO {
	
	long movcode;
	long ordercode;
	String branchid;
	String gcode;
	LocalDate movdate;
	long movquantity;
	String movstatus;
	
}
