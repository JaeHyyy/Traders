package com.exam.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.exam.dto.MovementDTO;

public interface MovementService {
	
	public List<MovementDTO> findAll();
	public List<MovementDTO> findByOrdercode(Long ordercode);
	public List<MovementDTO> findAllByBranchIdGroupedByDate(String branchid);
	
}
