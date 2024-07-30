package com.exam.service;

import java.util.List;

import com.exam.dto.StockDTO;
import com.exam.entity.Stock;

public interface StockService {
	List<StockDTO> findAll();
}
