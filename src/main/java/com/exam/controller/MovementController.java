package com.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.MovementDTO;
import com.exam.dto.StockDTO;
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
}
