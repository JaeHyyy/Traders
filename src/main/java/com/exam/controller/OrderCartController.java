package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	

	
	
	
	
	


	
	
}
