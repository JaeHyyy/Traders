package com.exam.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.GoodsDTO;
import com.exam.dto.StockDTO;
import com.exam.service.DisUseService;
import com.exam.service.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {
	
	StockService stockService;

	public StockController(StockService stockService) {
		this.stockService = stockService;

	}
	
	@GetMapping()
	public List<StockDTO> findAll(){
		return stockService.findAll();
	}

	// stock 테이블 + goods 테이블
    @GetMapping("/all-data")
    public List<StockDTO> findAllData() {
        return stockService.findAllData();
    }
    
    // 특정 gcode에 대한 stock 데이터 + goods 데이터 조회
    @GetMapping("/gcode-data/{gcode}")
    public List<StockDTO> findGcodeData(@PathVariable String gcode) {
        return stockService.findGcodeData(gcode);
    }
   

    //유통기한관리페이지에서 폐기완료 버튼 클릭시 stock테이블의 해당 데이터 삭제
	@DeleteMapping("/delete/{stockid}/{branchId}")
	public void deleteByBranchIdStock(@PathVariable int stockid, String branchid) {
		stockService.deleteByBranchIdStock(stockid, branchid);
	} 
    

	// 모바일 상세정보 페이지에서 위치정보 업데이트
	@PutMapping("/mobile-update-location")
	public void mobileUpdateLocation(@RequestParam String gcode,
	        @RequestParam String branchId,
	        @RequestParam String loc1,
	        @RequestParam String loc2,
	        @RequestParam String loc3,
	        @RequestParam(required = false) Integer price) {  // Integer로 수정, price가 선택사항임
		
	    // 매개변수 값을 출력
	    System.out.println("gcode: " + gcode);
	    System.out.println("branchId: " + branchId);
	    System.out.println("loc1: " + loc1);
	    System.out.println("loc2: " + loc2);
	    System.out.println("loc3: " + loc3);
	    System.out.println("price: " + (price != null ? price : 0));

	    stockService.mobileUpdateStockLocation(gcode, branchId, loc1, loc2, loc3, price);
	}



    
	// branchId 로 Stock 조회
    @GetMapping("/branch/{branchId}")
    public List<StockDTO> findByBranchIdStock(@PathVariable String branchId) {
        return stockService.findByBranchIdStock(branchId);
    }
    
    
    //검색창 입력값으로 stock조회
    @GetMapping("/branch/{branchId}/{keyword}")
    public List<StockDTO> searchStock(@PathVariable String branchId, @RequestParam String keyword) {
        return stockService.searchStock(branchId, keyword);
    }
    
    ///////////////////////////////////////////////////////////////////////
    
    // branchId 로 stock 테이블 + goods 테이블 데이터 조회
    @GetMapping("/all-data/{branchId}")
    public List<StockDTO> findAllDataByBranchId(@PathVariable String branchId) {
        return stockService.findAllDataByBranchId(branchId);
    }

}
