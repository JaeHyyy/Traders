package com.exam.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/{ordercode}")
	public List<MovementDTO> findByOrdercode(@PathVariable Long ordercode){
		return movementService.findByOrdercode(ordercode);
	}
	
	@GetMapping("/receipt")
	public List<MovementDTO> findGroupedByMovdate(){
		return movementService.findGroupedByMovdate();
	}
	
	@GetMapping("/movdateasc")
	public List<MovementDTO> findAllByOrderByMovdateAsc(){
		return movementService.findAllByOrderByMovdateAsc();
	}
	@GetMapping("/movdatedesc")
	public List<MovementDTO> findAllByOrderByMovdateDesc(){
		return movementService.findAllByOrderByMovdateDesc();
	}
}
