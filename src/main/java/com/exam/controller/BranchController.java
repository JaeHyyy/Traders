package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.BranchDTO;
import com.exam.service.BranchService;

@RestController
public class BranchController {
	
	BranchService branchService;

	public BranchController(BranchService branchService) {
		this.branchService = branchService;
	}
	
	@GetMapping("/hello")// Test용 경로
	public List<BranchDTO> findAll(){
		return branchService.findAll();
	}

}
