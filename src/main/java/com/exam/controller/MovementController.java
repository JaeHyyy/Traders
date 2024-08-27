package com.exam.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.MovementDTO;
import com.exam.dto.StockDTO;
import com.exam.entity.Movement;
import com.exam.service.MovementService;
import com.exam.service.StockService;

@RestController
@RequestMapping("/movement")
public class MovementController {
	
	@Autowired
	MovementService movementDataService;
	
	@Autowired
	StockService stockService;
	
	// movement 테이블 전체조회
	@GetMapping
	public List<MovementDTO> findAll(){
		return movementDataService.findAll();
	}
	
    @GetMapping("/{gcode}")
    public List<MovementDTO> findByGcode(@PathVariable String gcode) {
        return movementDataService.findByGcode(gcode);
    }
    
    @PostMapping("/ordersave")
    public ResponseEntity<List<Movement>> OrderSaveMovements(@RequestBody List<MovementDTO> movementDTOs) {
        try {
    	List<Movement> savedMovements = movementDataService.OrderSaveMovements(movementDTOs);
        return ResponseEntity.ok(savedMovements);
        }catch(Exception e){
        	// 오류 메시지를 로그에 남기고, 클라이언트에 반환
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    // 모바일 - branchId와 movdate로 조회
    @GetMapping("/{branchid}/{movdate}")
    public List<MovementDTO> findByBranchIdAndMovdate(@PathVariable String branchid, @PathVariable String movdate) {
        LocalDate date = LocalDate.parse(movdate);
        return movementDataService.findByBranchIdAndMovdate(branchid, date);
    }
    
    ///////////////////////////////////////////////////////////////////////
    
    // 모바일 status 변경 (대기 -> 완료)
//    @PostMapping("/updateMovStatus")
//    public ResponseEntity<?> updateMovStatus(@RequestBody List<Map<String, Object>> itemsToUpdate) {
//        try {
//            for (Map<String, Object> item : itemsToUpdate) {
//                String branchid = (String) item.get("branchid");
//                String gcode = (String) item.get("gcode");
//                String newStatus = (String) item.get("newStatus");
//                LocalDate movdate = LocalDate.parse((String) item.get("movdate")); // String을 LocalDate로 변환
//
//                movementDataService.updateMovStatusByBranchIdAndGcode(branchid, gcode, movdate, newStatus);
//            }
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            System.out.println("Error updating movement statuses: " + e);
//            return ResponseEntity.status(500).body("Error updating movement statuses: " + e.getMessage());
//        }
//    }
    
    @PostMapping("/updateMovStatus")
    public ResponseEntity<?> updateMovStatus(@RequestBody List<Map<String, Object>> itemsToUpdate) {
        Map<String, Object> item = null; // try-catch 블록 외부에서 item 변수 선언
        
        try {
            for (Map<String, Object> currentItem : itemsToUpdate) {
                item = currentItem; // 현재 처리 중인 item을 item 변수에 할당

                // 각 필드 값 추출
                String branchid = (String) item.get("branchid");
                String gcode = (String) item.get("gcode");
                String newStatus = (String) item.get("newStatus");
                String movdateString = (String) item.get("movdate");
                String loc1 = (String) item.get("loc1");
                String loc2 = (String) item.get("loc2");
                String loc3 = (String) item.get("loc3");
                
                // Safely convert gprice from String or Object to Integer
                Integer gprice = null;
                if (item.get("gprice") instanceof String) {
                    gprice = Integer.parseInt((String) item.get("gprice"));
                } else if (item.get("gprice") instanceof Integer) {
                    gprice = (Integer) item.get("gprice");
                }

                System.out.println("loc1: " + loc1);
                System.out.println("loc2: " + loc2);
                System.out.println("loc3: " + loc3);
                System.out.println("gprice: " + gprice);
                
                // null 체크 및 변환
                if (branchid == null || gcode == null || newStatus == null || movdateString == null) {
                    throw new IllegalArgumentException("필수 값이 누락되었습니다: " + item);
                }

                LocalDate movdate = LocalDate.parse(movdateString); // String을 LocalDate로 변환

                // Movement 상태 업데이트
                movementDataService.updateMovStatusByBranchIdAndGcode(branchid, gcode, movdate, newStatus);

                // "입고 완료"로 상태가 변경된 항목만 Stock 테이블에 삽입
                if ("입고 완료".equals(newStatus)) {
                    Integer movquantity = null;
                    if (item.get("movquantity") instanceof String) {
                        movquantity = Integer.parseInt((String) item.get("movquantity"));
                    } else if (item.get("movquantity") instanceof Integer) {
                        movquantity = (Integer) item.get("movquantity");
                    }

                    if (movquantity == null) {
                        throw new IllegalArgumentException("movquantity 값이 누락되었습니다: " + item);
                    }

                    // StockDTO 생성 및 데이터 설정
                    StockDTO stockDTO = new StockDTO();
                    stockDTO.setBranchid(branchid);
                    stockDTO.setGcode(gcode); // gcode를 설정
                    stockDTO.setStockquantity(movquantity);
                    stockDTO.setExpdate(movdate);
                    stockDTO.setLoc1(loc1 != null ? loc1 : "loc1"); // 기본값 사용
                    stockDTO.setLoc2(loc2 != null ? loc2 : "loc2"); // 기본값 사용
                    stockDTO.setLoc3(loc3 != null ? loc3 : "loc3"); // 기본값 사용
                    stockDTO.setGprice(gprice != null ? gprice : 0); // 가격이 null이면 0으로 설정

                    System.out.println("입고된 데이터: " + item);
                    System.out.println("StockDTO: " + stockDTO);
                    
                    // Stock 테이블에 데이터 삽입
                    stockService.saveStock(stockDTO);
                }

                
                
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error updating movement statuses for item: " + item);
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error updating movement statuses: " + e.getMessage());
        }
    }



    // branchId 기준으로 movstatus 가 반려인 데이터 조회
    @GetMapping("/rejected/{branchid}")
    public List<MovementDTO> findRejectedMovements(@PathVariable String branchid) {
        return movementDataService.findRejectedMovementsByBranchId(branchid);
    }





}
