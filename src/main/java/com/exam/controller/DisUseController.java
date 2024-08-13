package com.exam.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.DisUseDTO;
import com.exam.service.DisUseService;

@RestController
@RequestMapping("/disuse")
//@CrossOrigin(origins = "http://localhost:3000") // React 앱이 실행되는 포트
public class DisUseController {
	
	DisUseService disUseService;
    // 마지막 실행 시간을 저장할 변수 (예: 데이터베이스, 파일 등)
    private LocalDateTime lastExecutionTime;

	public DisUseController(DisUseService disUseService) {
		this.disUseService = disUseService;
	}
	
	//현재 날짜 기준 유통기한 지난 상품 stock 테이블에서 삭제 및 disuse 테이블에 저장
    @PostMapping("/exp/{branchId}")
    public ResponseEntity<String> moveExpiredStocks(@RequestBody List<String> gcodes,@PathVariable String branchId) {
        try {
        	disUseService.moveExpiredStocksToDisuse(branchId);
            return ResponseEntity.ok("Expired stocks moved successfully");
        } catch (Exception e) {
        	System.out.println("Error occurred while moving expired stocks"+e.getMessage());
        	e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while moving expired stocks: " + e.getMessage());
        }
    }
    //12시에 자동 실행 
//    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행
//    public void scheduleExpiredStock(String branchId) {
//    	disUseService.moveExpiredStocksToDisuse(branchId);
//    }
    
    
    //유통기한관리페이지에서 삭제 버튼 클릭시 stock테이블의 해당 데이터 삭제
	@DeleteMapping("/delete/{disid}/{branchId}")
	public void deleteByBranchIdDisuse(@PathVariable int disid,String branchId) {
		disUseService.deleteByBranchIdDisuse(disid,branchId);
	}
	
	
	//유통기한관리페이지에서 폐기완료 버튼 누르면 disuse테이블의 disdate에 현재 날짜 데이터로 업데이트하기
	@PutMapping("/update/{disid}/{branchId}")
	public void updateByBranchIdDisuse(@PathVariable int disid,String branchId, @RequestBody DisUseDTO dto) {
		disUseService.updateByBranchIdDisuse(disid,branchId, dto);
	}
	
	
	// branchId 로 disuse 조회
    @GetMapping("/branch/{branchId}")
    public List<DisUseDTO> findByBranchIdDisuse(@PathVariable String branchId) {
        return disUseService.findByBranchIdDisuse(branchId);
    }
    
    
    
//    @PostMapping("/checkDuplicates/{branchId}")
//    public ResponseEntity<Map<String, Object>> checkDuplicates(
//        @PathVariable String branchId,
//        @RequestBody List<String> gcodes) {
//        
//        List<String> duplicates = disUseService.findDuplicateGcodes(gcodes, branchId);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("duplicates", duplicates);
//        
//        System.out.println("Duplicate gcodes: " + duplicates);
//        System.out.println("Received gcodes: " + gcodes);
//        System.out.println("Received branchId: " + branchId);
//        
//        return ResponseEntity.ok(response);
//    }

	
	


	
	
}
