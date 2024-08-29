
package com.exam.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.exam.dto.GnameSummaryDTO;
import com.exam.dto.StockDTO;
import com.exam.dto.UserStockDTO;
import com.exam.entity.Stock;

public interface StockService {
	
	List<StockDTO> findAll();

	
	// stock 테이블 + goods 테이블 데이터 조회
	List<StockDTO> findAllData();
	// 특정 gcode에 대한 stock 데이터 + goods 데이터 조회
	List<StockDTO> findGcodeData(String gcode);
	// 특정 gcode 와 branchId 에 대한 stock 데이터 + goods 데이터 조회
	List<StockDTO> findByGcodeDataAndBranchId(@PathVariable String gcode, @PathVariable String branchid);

	List<StockDTO> findByGoodsGcode(String gcode);
	void updateStockLocation(String gcode, String loc1, String loc2, String loc3);
	public void deleteByBranchIdStock(int stockid, String branchId);
	
	// 모바일 - 상세정보페이지의 위치 및 가격 업데이트
	void mobileUpdateStockLocation(String gcode, String branchId, String loc1, String loc2, String loc3, Integer price);


	
	//admin - 그래프
	public List<UserStockDTO> countStocksByBranch();
	public List<GnameSummaryDTO> findGnameSummaries();
	
	// branchId 로 Stock 조회
	public List<StockDTO> findByBranchIdStock(String branchId);
	
	//발주하기 버튼
	public void saveAll(String branchId,List<StockDTO> dtos);
	
	//검색창 입력값으로 stock조회
	public List<StockDTO> searchStock(String branchId, String keyword);
	
	
	//////////////////////////////////////////////////////////////////////
	
    // branchId로 stock 테이블 + goods 테이블 데이터 조회
    List<StockDTO> findAllDataByBranchId(String branchId);
	
    void saveStock(StockDTO stockDTO);
	


}
