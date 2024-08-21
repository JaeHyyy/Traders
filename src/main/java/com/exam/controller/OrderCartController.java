package com.exam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.exam.dto.OrderCartDTO;
import com.exam.service.OrderCartService;

@RestController
@RequestMapping("/ordercart")
@CrossOrigin(origins = "http://localhost:3000") // React 앱이 실행되는 포트
public class OrderCartController {
	
	OrderCartService orderCartService;
	Map<String, String> branchOrderCodeMap; // branchId와 ordercode를 매핑하는 Map

	public OrderCartController(OrderCartService orderCartService) {
		this.orderCartService = orderCartService;
		this.branchOrderCodeMap = new HashMap<>(); // 초기화
	}


	//메인페이지에서 본사 상품 전체 조회	
	@GetMapping
	public List<OrderCartDTO> findAll() {
		return orderCartService.findAll();
	}
	
//	//메인에서 발주하기 눌렀을 때 ordercart db테이블에 해당 상품 저장 
//	@Transactional
//	 @PostMapping("/saveAll")
//	    public ResponseEntity<String> saveAll(@RequestBody List<OrderCartDTO> dtos) {
//		 System.out.println("Received OrderCartDTOs: " + dtos); // 요청된 데이터 로그
//	        orderCartService.saveAll(dtos);
//	        return ResponseEntity.ok("OrderCartDTOs saved successfully");
//	    }
//	
//	//발주하기 페이지에서 선택 후 담았던 상품 삭제 
//	@DeleteMapping("/delete/{ordercode}")
//	public void delete(@PathVariable int ordercode) {
//		orderCartService.delete(ordercode);
//	}
//	
//	//발주하기 페이지에서 수량 변경 후 수정 내용 변경하기
//	@PutMapping("/update/{ordercode}")
//	public void update(@PathVariable int ordercode, 
//			@RequestBody OrderCartDTO dto) {
//		orderCartService.update(ordercode, dto);
//	}
//	

	// ------- ▼ security 적용후 -------------------------------------------------------
	
	// branchId 로 OrderCart 조회
    @GetMapping("/branch/{branchId}")
    public List<OrderCartDTO> findByBranchId(@PathVariable String branchId) {
        return orderCartService.findByBranchId(branchId);
    }
    
    @Transactional
    @PostMapping("/saveAll/{branchId}")
    public ResponseEntity<String> saveAll(@PathVariable String branchId, @RequestBody List<OrderCartDTO> dtos) {
        try {
            if (dtos == null || dtos.isEmpty()) {
                return ResponseEntity.badRequest().body("OrderCartDTOs are empty or null");
            }

            // 해당 branchId에 대해 ordercode가 이미 존재하는지 확인
            String ordercode = branchOrderCodeMap.get(branchId);
            if (ordercode == null) {
                // 존재하지 않으면 새로 생성하고 Map에 저장
                ordercode = generateOrdercode();
                branchOrderCodeMap.put(branchId, ordercode);
            }

            // 각 DTO에 ordercode를 설정
            for (OrderCartDTO dto : dtos) {
                if (dto.getOrdercode() == null) {
                    dto.setOrdercode(ordercode);
                }
            }

            orderCartService.saveAll(branchId, dtos);
            return ResponseEntity.ok("OrderCartDTOs saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
    
    private String generateOrdercode() {
        Random random = new Random();
        
        // 1000~9999 사이의 4자리 랜덤 숫자를 생성하여 `-`로 구분하여 연결
        String part1 = String.format("%04d", random.nextInt(9000) + 1000);  // 1000~9999
        String part2 = String.format("%04d", random.nextInt(9000) + 1000);  // 1000~9999
        String part3 = String.format("%04d", random.nextInt(9000) + 1000);  // 1000~9999
        
        return part1 + "-" + part2 + "-" + part3;
    }


    // 결제성공시 상품 삭제
    @DeleteMapping("/delete/{branchId}/{ordercode}")
    public void delete(@PathVariable String branchId, @PathVariable String ordercode) {
        orderCartService.delete(branchId, ordercode);
    }

    //발주하기 페이지에서 수량 변경 후 수정 내용 변경하기
    @PutMapping("/update/{branchId}/{ordercode}")
    public void update(@PathVariable String branchId, @PathVariable String ordercode, @RequestBody OrderCartDTO dto) {
        orderCartService.update(branchId, ordercode, dto);
    }
   	
}
