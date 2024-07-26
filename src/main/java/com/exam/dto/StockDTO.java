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
public class StockDTO {
	int stockid;
	String gcode;
	Integer stockquantity;
	LocalDate expdate;
	Integer gprice;
	String loc1;
	String loc2;
	String loc3;
	Goods goods;
}
