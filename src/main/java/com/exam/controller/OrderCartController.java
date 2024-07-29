package com.exam.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.OrderCartDTO;
import com.exam.service.OrderCartService;

@RestController
@RequestMapping("/ordercart")
@CrossOrigin(origins = "http://localhost:3000") // React 앱이 실행되는 포트
public class OrderCartController {
	
	OrderCartService orderCartService;

	public OrderCartController(OrderCartService orderCartService) {
		this.orderCartService = orderCartService;
	}


	//본사 상품 전체 조회	
	@GetMapping
	public List<OrderCartDTO> findAll() {
		return orderCartService.findAll();
	}
	
	//메인에서 발주하기 눌렀을 때 
	@Transactional
	 @PostMapping("/saveAll")
	    public ResponseEntity<String> saveAll(@RequestBody List<OrderCartDTO> dtos) {
		 System.out.println("Received OrderCartDTOs: " + dtos); // 요청된 데이터 로그
	        orderCartService.saveAll(dtos);
	        return ResponseEntity.ok("OrderCartDTOs saved successfully");
	    }

	
	
	
	

	
	
	
	
	


	
	
}
