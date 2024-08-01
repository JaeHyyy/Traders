package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.DisUseDTO;
import com.exam.service.DisUseService;

@RestController
@RequestMapping("/disuse")
@CrossOrigin(origins = "http://localhost:3000") // React 앱이 실행되는 포트
public class DisUseController {
	
	DisUseService disUseService;

	public DisUseController(DisUseService disUseService) {
		this.disUseService = disUseService;
	}

	//sql disuse 테이블 값 전체 조회
	@GetMapping
	public List<DisUseDTO> findAll() {
		return disUseService.findAll();
	}
	
	
	//현재 날짜 기준 유통기한 지난 상품 stock 테이블에서 삭제 및 disuse 테이블에 저장
    @PostMapping("/exp")
    public ResponseEntity<String> moveExpiredStocks() {
        try {
        	disUseService.moveExpiredStocksToDisuse();
            return ResponseEntity.ok("Expired stocks moved successfully");
        } catch (Exception e) {
        	System.out.println("Error occurred while moving expired stocks"+e.getMessage());
        	e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while moving expired stocks: " + e.getMessage());
        }
    }
	
	

	
	
	
	

	
	
	
	
	


	
	
}
