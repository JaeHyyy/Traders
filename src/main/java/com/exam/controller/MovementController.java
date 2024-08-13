package com.exam.controller;

import java.util.List;

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
import com.exam.entity.Movement;
import com.exam.service.MovementService;

@RestController
@RequestMapping("/movement")
public class MovementController {
	
	@Autowired
	MovementService movementDataService;
	
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
    
//    @PostMapping("/{ordercode}")
//    public List<MovementDTO> findByOrdercode(@PathVariable String ordercode) {
//        return movementDataService.findByOrdercode(ordercode);
//    }
}
