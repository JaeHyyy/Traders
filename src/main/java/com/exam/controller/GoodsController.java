package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.GoodsDTO;
import com.exam.service.GoodsService;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "http://localhost:3000") // React 앱이 실행되는 포트
public class GoodsController {
	
	GoodsService goodsService;

	public GoodsController(GoodsService goodsService) {
		this.goodsService = goodsService;
	}
	
	//본사 상품 전체 조회	
	@GetMapping
	public List<GoodsDTO> findAll() {
		return goodsService.findAll();
	}
	
	
	//상품명, 카테고리명, 상품코드로 검색 조회
	//본사 상품의 상품명 검색으로 해당 상품 조회
	@GetMapping("/{gname}")
	public List<GoodsDTO> findByGname(@PathVariable String gname) {
		return goodsService.findByGname(gname);
	}
	//본사 상품의 카테고리명 검색으로 해당 상품 조회
	@GetMapping("/{gcategory}")
	public List<GoodsDTO> findByGcategory(@PathVariable String gcategory) {
		return goodsService.findByGcategory(gcategory);
	}


	
	
}
