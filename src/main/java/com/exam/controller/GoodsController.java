package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.EmpDTO;
import com.exam.dto.GoodsDTO;
import com.exam.entity.Goods;
import com.exam.service.GoodsService;

@RestController
public class GoodsController {
	
	GoodsService goodsService;

	public GoodsController(GoodsService goodsService) {
		this.goodsService = goodsService;
	}
	
	//본사 상품 전체 조회
	@GetMapping("/home")
	public List<GoodsDTO> findAll() {
		return goodsService.findAll();
	}
	
	
	//본사 상품의 상품명 검색으로 해당 상품 조회
	@GetMapping("/home/{gname}")
	public List<GoodsDTO> findByGname(@PathVariable String gname) {
		return goodsService.findByGname(gname);
	}


	
	
}
