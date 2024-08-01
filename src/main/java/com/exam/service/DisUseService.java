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

	public List<DisUseDTO> findAll();
	 void moveExpiredStocksToDisuse();
//	void saveAll(List<OrderCartDTO> dtos);
//	public void delete( int ordercode);
//	public void update(int ordercode,OrderCartDTO dto);
}
