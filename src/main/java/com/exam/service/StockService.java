package com.exam.service;

import java.util.List;

import com.exam.dto.StockDTO;
import com.exam.entity.Stock;

public interface StockService {
	List<StockDTO> findAll();
	List<StockDTO> findByGoodsGcode(String gcode);
	void updateStockLocation(String gcode, String loc1, String loc2, String loc3);
}
