package com.exam.service;

import java.util.List;

import com.exam.dto.BranchDTO;

public interface BranchService {
	
	public int save(BranchDTO branchDTO);
	List<BranchDTO> findAll();
	
}
