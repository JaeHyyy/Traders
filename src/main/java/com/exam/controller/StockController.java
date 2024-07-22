package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.StockDTO;
import com.exam.service.StockService;

@RestController
public class StockController {
	
	StockService stockService;

	public StockController(StockService stockService) {
		this.stockService = stockService;
	}
	
	@GetMapping("/hello2")
	public List<StockDTO> findAll(){
		return stockService.findAll();
	}
	
}
