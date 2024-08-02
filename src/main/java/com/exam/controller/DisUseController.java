package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.DisUseDTO;
import com.exam.dto.OrderCartDTO;
import com.exam.service.DisUseService;

@RestController
@RequestMapping("/disuse")
//@CrossOrigin(origins = "http://localhost:3000") // React 앱이 실행되는 포트
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
    
    //유통기한관리페이지에서 폐기완료 버튼 클릭시 stock테이블의 해당 데이터 삭제
	@DeleteMapping("/delete/{disid}")
	public void delete(@PathVariable int disid) {
		disUseService.delete(disid);
	}
	
	
	//유통기한관리페이지에서 폐기완료 버튼 누르면 disuse테이블의 disdate에 현재 날짜 데이터로 업데이트하기
	@PutMapping("/update/{disid}")
	public void update(@PathVariable int disid, @RequestBody DisUseDTO dto) {
		disUseService.update(disid, dto);
	}
	
	

	
	
	
	

	
	
	
	
	


	
	
}
