package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.StockDTO;
import com.exam.service.StockService;

@RestController
@RequestMapping("/stock")
@CrossOrigin(origins = "http://localhost:3000")
public class StockController {
	
	StockService stockService;

	public StockController(StockService stockService) {
		this.stockService = stockService;
	}
	
	@GetMapping
	public List<StockDTO> findAll(){
		return stockService.findAll();
	}
	
}
