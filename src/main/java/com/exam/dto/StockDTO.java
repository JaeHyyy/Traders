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
	String branchid;
	String gcode;
	Integer stockquantity;
	LocalDate expdate;
	Integer gprice;
	String loc1;
	String loc2;
	String loc3;
	Goods goods;
	
	// stock 테이블 + goods 테이블
	// Goods 로 받는게 아닌 GoodsDTO 로 받는것이라 참고할것.
	GoodsDTO goodsData;
}
