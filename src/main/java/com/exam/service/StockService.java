package com.exam.service;

import java.util.List;

import com.exam.dto.StockDTO;

public interface StockService {
	
	List<StockDTO> findAll();
	
	// stock 테이블 + goods 테이블 데이터 조회
	List<StockDTO> findAllData();
	// 특정 gcode에 대한 stock 데이터 + goods 데이터 조회
	List<StockDTO> findGcodeData(String gcode);
}
