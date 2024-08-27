package com.exam.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.GoodsDTO;
import com.exam.dto.MovementDTO;
import com.exam.dto.MovementGoodsDTO;
import com.exam.dto.StockDTO;
import com.exam.service.GoodsService;
import com.exam.service.MovementService;
import com.exam.service.StockService;

@RestController
public class ReceiptModifyController {
	
	MovementService movementService;
	StockService stockService;
	GoodsService goodsService;

	public ReceiptModifyController(MovementService movementService, StockService stockService, GoodsService goodsService) {
		this.movementService = movementService;
		this.stockService = stockService;
		this.goodsService = goodsService;
	}
	
	
	@GetMapping("/{branchid}/receiptmodify")
    public List<MovementDTO> findByMovdate(@PathVariable String branchid, @RequestParam("movdate") LocalDate movdate) {
        return movementService.findByMovdate(branchid, movdate);
    }
	
	@GetMapping("/getLocation")
	public List<StockDTO> findByGoodsGcode(@RequestParam String gcode){
		return stockService.findByGoodsGcode(gcode);
	}
	
	
	@PutMapping("/updateLocation")
    public void updateStockLocation(
            @RequestParam String gcode, 
            @RequestParam String loc1, 
            @RequestParam String loc2, 
            @RequestParam String loc3) {
        stockService.updateStockLocation(gcode, loc1, loc2, loc3);
    }
	

	
	@GetMapping("/{branchid}/join")
	public List<MovementGoodsDTO> findMovementsWithGoodsByMovdate(@PathVariable String branchid, @RequestParam("movdate") LocalDate movdate){
		return movementService.findMovementsWithGoodsByMovdate(branchid, movdate);
	}
    
	
}
