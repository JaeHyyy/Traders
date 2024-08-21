package com.exam.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.exam.dto.GoodsDTO;
import com.exam.dto.OrderCartDTO;
import com.exam.entity.Goods;
import com.exam.entity.OrderCart;


public interface OrderCartService {

	public List<OrderCartDTO> findAll();
	
//	void saveAll(List<OrderCartDTO> dtos);
//	public void delete( int ordercode);
//	public void update(int ordercode,OrderCartDTO dto);
	
	// ▼ security 적용 후
	// branchId 로 OrderCart 조회
	List<OrderCartDTO> findByBranchId(String branchId);
    void saveAll(String branchId, List<OrderCartDTO> dtos);
    
    // 결제성공시 상품 삭제
    void delete(String branchId, String ordercode);
    void update(String branchId, String ordercode, OrderCartDTO dto);
}
