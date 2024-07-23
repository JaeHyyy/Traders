package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.MovementDTO;
import com.exam.service.MovementService;

@RestController
public class IncomeController {
	
	MovementService movementService;

	public IncomeController(MovementService movementService) {
		this.movementService = movementService;
	}
	
	@GetMapping("/income")
	public List<MovementDTO> findAll(){
		return movementService.findAll();
	}
	
}