package com.exam.controller;

import java.util.List;

import com.exam.dto.StockDTO;

public interface StockService {
	List<StockDTO> findAll();
}
