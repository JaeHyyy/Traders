package com.exam.service;

import java.util.List;

import com.exam.dto.StockDTO;

public interface StockService {
	List<StockDTO> findAll();
}
