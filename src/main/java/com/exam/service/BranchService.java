package com.exam.service;

import java.util.List;
import java.util.Optional;

import com.exam.dto.BranchDTO;

public interface BranchService {
	
	void save(BranchDTO branchDTO);
	List<BranchDTO> findAll();
}
