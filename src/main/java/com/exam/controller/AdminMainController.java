package com.exam.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exam.dto.GnameSummaryDTO;
import com.exam.dto.GoodsDTO;
import com.exam.dto.MovementGoodsDTO;
import com.exam.dto.NoticeDTO;
import com.exam.dto.UserDTO;
import com.exam.dto.UserStockDTO;
import com.exam.entity.Notice;
import com.exam.entity.User;
import com.exam.repository.UserRepository;
import com.exam.service.GoodsService;
import com.exam.service.MovementService;
import com.exam.service.NoticeService;
import com.exam.service.StockService;
import com.exam.service.UserService;

@RestController
public class AdminMainController {
	
	UserRepository userRepository;
	
	StockService stockService;
	MovementService movementService;
	GoodsService goodsService;
	NoticeService noticeService;
	UserService userService;

	public AdminMainController(StockService stockService, MovementService movementService, GoodsService goodsService, NoticeService noticeService, UserService userService) {
		this.stockService = stockService;
		this.movementService = movementService;
		this.goodsService = goodsService;
		this.noticeService = noticeService;
		this.userService = userService;
	}
	
	@GetMapping("/barchart")
	public List<UserStockDTO> countStocksByBranch() {
		return stockService.countStocksByBranch();
	}
	
	@GetMapping("/chart2")
	public List<GnameSummaryDTO> findGnameSummaries() {
		return stockService.findGnameSummaries();
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
	
	@PostMapping("/goodsadd")
    public ResponseEntity<GoodsDTO> addGoods(
        @RequestPart("goods") GoodsDTO goodsDTO, 
        @RequestPart("file") MultipartFile file) {
        
        goodsDTO.setMultipartFile(file);
        GoodsDTO createdGoods = goodsService.addGoods(goodsDTO);
        return ResponseEntity.ok(createdGoods);
    }
	
	//메세지 보내기
	@PostMapping("/sendnotice")
    public ResponseEntity<Notice> sendNotice(@RequestBody Notice notice) {
        Notice savedNotice = noticeService.sendNotice(notice);
        return ResponseEntity.ok(savedNotice);
    }
	
	// 지점 메세지 받기
    @GetMapping("/getnotice/{branchId}")
    public ResponseEntity<List<NoticeDTO>> getNoticesForBranch(@PathVariable String branchId) {
        List<NoticeDTO> notices = noticeService.getNoticesForBranch(branchId);
        return ResponseEntity.ok(notices);
    }
    
    // 전체 메시지 받기
    @GetMapping("/globalnotice")
    public ResponseEntity<List<NoticeDTO>> getAllGlobalNotices() {
        List<NoticeDTO> notices = noticeService.getAllGlobalNotices();
        return ResponseEntity.ok(notices);
    }
	
    @GetMapping("/sendbranch")
    public List<UserDTO> getAllBranches() {
        return userService.findAll();
    }
    
    //모든 공지 조회
    @GetMapping("/notices")
    public ResponseEntity<List<NoticeDTO>> getAllNotices() {
        List<NoticeDTO> notices = noticeService.findAllNotices();
        return ResponseEntity.ok(notices);
    }
    
    // 공지 삭제 
    @DeleteMapping("/deletenotice/{noticeId}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteNoticeById(noticeId);
        return ResponseEntity.ok("Notice deleted successfully");
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
	
