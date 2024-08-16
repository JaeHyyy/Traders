package com.exam.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.MovementDTO;
import com.exam.dto.MovementGoodsDTO;
import com.exam.dto.UserDTO;
import com.exam.dto.UserStockDTO;
import com.exam.entity.User;
import com.exam.repository.UserRepository;
import com.exam.service.MovementService;
import com.exam.service.StockService;

@RestController
public class AdminMainController {
	
	StockService stockService;
	MovementService movementService;
	UserRepository userRepository;

	public AdminMainController(StockService stockService, MovementService movementService) {
		this.stockService = stockService;
		this.movementService = movementService;
	}
	
	@GetMapping("/barchart")
	public List<UserStockDTO> countStocksByBranch() {
		return stockService.countStocksByBranch();
	}
	
	@GetMapping("/adminmovement")
	public List<Object[]> getBranchMovements(){
		return movementService.findBranchMovements();
	}
	
	@GetMapping("/adminmov")
    public List<MovementGoodsDTO> getPendingMovementsByBranchAndDate(@RequestParam String branchName,
                                                                     @RequestParam LocalDate movdate,
                                                                     @RequestParam String movstatus) {
        return movementService.findPendingMovementsByBranchAndDate(branchName, movdate, movstatus);
    }
	
	@PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestBody StatusUpdateRequest request) {
        movementService.updateMovstatusForGroup(request.getBranchName(), request.getMovdate(), request.getMovstatus());
        return ResponseEntity.ok("Status updated successfully");
    }
	
	public static class StatusUpdateRequest {
        private String branchName;
        private LocalDate movdate;
        private String movstatus;

        // Getters and Setters
        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public LocalDate getMovdate() {
            return movdate;
        }

        public void setMovdate(LocalDate movdate) {
            this.movdate = movdate;
        }

        public String getMovstatus() {
            return movstatus;
        }

        public void setMovstatus(String movstatus) {
            this.movstatus = movstatus;
        }
    }
}
	
