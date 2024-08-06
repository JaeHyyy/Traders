package com.exam.repository;


import java.sql.Date;
import java.util.List;


import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.dto.StockDTO;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.Movement;


import com.exam.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer>{
	
	@Modifying
    @Transactional
    @Query("UPDATE Stock s SET s.loc1 = :loc1, s.loc2 = :loc2, s.loc3 = :loc3 WHERE s.goods.gcode = :gcode")
    void updateLocationByGcode(String gcode, String loc1, String loc2, String loc3);
	
	//유통기한 지난 재고 상품들 disuse테이블로 데이터 저장 시키기
	@Query("SELECT s FROM Stock s WHERE s.expdate < CURRENT_DATE")
	List<Stock> findByExpdate(Date expdate);
	//stock테이블에서 유통기한 안 지난 재고 상품들만 보여주기
	@Query("SELECT s FROM Stock s WHERE s.expdate >= :currentDate")
    List<Stock> findAllValidStocks(LocalDate currentDate);
	

	// stock 테이블 + goods 테이블
    @Query("SELECT s FROM Stock s JOIN FETCH s.goods")
    List<Stock> findAllWithGoods();
    
    // 특정 gcode에 대한 stock 데이터 조회
    @Query("SELECT s FROM Stock s JOIN FETCH s.goods WHERE s.goods.gcode = :gcode")
    List<Stock> findByGcode(String gcode);

	List<Stock> findByGoodsGcode(String gcode);
	
	// 모바일 - 상세정보페이지 위치 업데이트
    @Modifying
    @Query("UPDATE Stock s SET s.loc1 = :loc1, s.loc2 = :loc2, s.loc3 = :loc3 WHERE s.goods.gcode = :gcode")
    void mobileUpdateLocationByGcode(String gcode, String loc1, String loc2, String loc3);


}
