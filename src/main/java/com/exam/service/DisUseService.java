package com.exam.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.exam.dto.DisUseDTO;
import com.exam.dto.GoodsDTO;
import com.exam.dto.OrderCartDTO;
import com.exam.entity.Goods;


public interface DisUseService {


	 void moveExpiredStocksToDisuse(String branchId);
	 public void deleteByBranchIdDisuse(int disid,String branchId);
	 public void updateByBranchIdDisuse(int disid,String branchId,DisUseDTO dto);
	 public List<DisUseDTO> findByBranchIdDisuse(String branchId);
	 
//	 public List<String> findDuplicateGcodes(List<String> gcodes, String branchId);

}
