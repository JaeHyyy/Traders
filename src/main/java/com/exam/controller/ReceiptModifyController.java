package com.exam.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.MovementDTO;
import com.exam.service.MovementService;

@RestController
public class ReceiptModifyController {
	
	MovementService movementService;

	public ReceiptModifyController(MovementService movementService) {
		this.movementService = movementService;
	}
	
	
	@GetMapping("/receiptmodify")
    public List<MovementDTO> findByMovdate(@RequestParam("movdate") LocalDate movdate) {
        return movementService.findByMovdate(movdate);
    }
}
