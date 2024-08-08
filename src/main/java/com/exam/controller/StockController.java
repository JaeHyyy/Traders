package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.OrderCartDTO;
import com.exam.dto.StockDTO;
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
	@DeleteMapping("/delete/{stockid}")
	public void delete(@PathVariable int stockid) {
		stockService.delete(stockid);
	} 
    

    // 모바일 상세정보 페이지에서 위치정보 업데이트
    @PutMapping("/mobile-update-location")
    public void mobileUpdateLocation(@RequestParam String gcode,
            @RequestParam String loc1,
            @RequestParam String loc2,
            @RequestParam String loc3) {
    	stockService.mobileUpdateStockLocation(gcode, loc1, loc2, loc3);
    }
    
	// branchId 로 Stock 조회
    @GetMapping("/branch/{branchId}")
    public List<StockDTO> findByBranchIdStock(@PathVariable String branchId) {
        return stockService.findByBranchIdStock(branchId);
    }


}
