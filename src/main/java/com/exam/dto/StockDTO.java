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
public class StockDTO {
	int stockid;
	String gcode;
	int stockquantity;
	LocalDate expdate;
	int gprice;
	String loc1;
	String loc2;
	String loc3;
}
