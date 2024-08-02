
package com.exam.service;

import java.util.List;

import com.exam.dto.StockDTO;
import com.exam.entity.Stock;

public interface StockService {
	
	List<StockDTO> findAll();

	
	// stock 테이블 + goods 테이블 데이터 조회
	List<StockDTO> findAllData();
	// 특정 gcode에 대한 stock 데이터 + goods 데이터 조회
	List<StockDTO> findGcodeData(String gcode);

	List<StockDTO> findByGoodsGcode(String gcode);
	void updateStockLocation(String gcode, String loc1, String loc2, String loc3);
	
	// 모바일 - 상세정보페이지의 위치업데이트
	void mobileUpdateStockLocation(String gcode, String loc1, String loc2, String loc3);

}
