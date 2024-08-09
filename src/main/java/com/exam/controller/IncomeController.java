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
	
	

	@GetMapping("/{branchid}/receipt")
	public List<MovementDTO> findGroupedByMovdate(@PathVariable String branchid){
		return movementService.findGroupedByMovdate(branchid);
	}
	
	@GetMapping("/movdateasc")
	public List<MovementDTO> findAllByOrderByMovdateAsc(){
		return movementService.findAllSortedByDate();
	}
	@GetMapping("/movdatedesc")
	public List<MovementDTO> findAllByOrderByMovdateDesc(){
		return movementService.findAllByOrderByMovdateDesc();
	}
}
