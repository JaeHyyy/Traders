package com.exam.controller;

import java.util.List;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exam.dto.GoodsDTO;
import com.exam.entity.Goods;
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
	
	
	//입력값으로 해당 상품 조회
	@GetMapping("/{keyword}")
	 public List<GoodsDTO> search(@PathVariable String keyword) {
	        return goodsService.search(keyword);
	    }
	
    @PostMapping("/save")
    public Goods save(@ModelAttribute GoodsDTO goodsDTO, @RequestParam("file") MultipartFile file) {
        return goodsService.save(goodsDTO, file);
    }
	
	
	
	
	


	
	
}
