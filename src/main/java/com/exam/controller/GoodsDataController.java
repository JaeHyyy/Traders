package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.service.GoodsService;
import com.exam.dto.GoodsDTO;

@RestController
@RequestMapping("/goods")
public class GoodsDataController {
	
	GoodsService goodsDataService;

	public GoodsDataController(GoodsService goodsDataService) {
		super();
		this.goodsDataService = goodsDataService;
	}
	
	// 모바일데이터용 전체상품조회
	@GetMapping
	public List<GoodsDTO> findAllData() {
		return goodsDataService.findAllData();
	}
	
    // gcode로 상품 조회
    @GetMapping("/{gcode}")
    public GoodsDTO findByGcode(@PathVariable String gcode) {
        return goodsDataService.findByGcode(gcode);
    }
}
