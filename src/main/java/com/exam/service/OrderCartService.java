package com.exam.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.exam.dto.GoodsDTO;
import com.exam.dto.OrderCartDTO;
import com.exam.entity.Goods;


public interface OrderCartService {

	public List<OrderCartDTO> findAll();
	
//	void saveAll(List<OrderCartDTO> dtos);
//	public void delete( int ordercode);
//	public void update(int ordercode,OrderCartDTO dto);
	
	// ▼ security 적용 후
	// branchId 로 OrderCart 조회
	List<OrderCartDTO> findByBranchId(String branchId);
    void saveAll(String branchId, List<OrderCartDTO> dtos);
    void delete(String branchId, int ordercode);
    void update(String branchId, int ordercode, OrderCartDTO dto);
}
