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
	
	@GetMapping("/login") //test 하려고 security 피해서 적은거임. 쓰셈!
	public List<BranchDTO> findAll(){
		return branchService.findAll();
	}

}
