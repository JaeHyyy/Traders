package com.exam.dto;

import java.time.LocalDate;

import com.exam.entity.Goods;

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
	
	long movidx;
	long movcode;
	Long ordernum;
	String branchid;
	String gcode;
	LocalDate movdate;
	long movquantity;
	String movstatus;
	long count;
	String ordercode;
}
