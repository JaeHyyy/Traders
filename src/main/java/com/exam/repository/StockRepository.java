package com.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.dto.StockDTO;
import com.exam.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer>{
	
	/*
	 * 다음 메서드가 지원됨
	 * findAll()
	 * findById()
	 * count()
	 * delete()
	 * deleteById()
	 * 
	 * 이외의 변수로 조회하기 위해서는 추가로 메서드를 정의해야 된다.( Query Method 규칙을 따름 )
	 */
	
	// stock 테이블 + goods 테이블
    @Query("SELECT s FROM Stock s JOIN FETCH s.goods")
    List<Stock> findAllWithGoods();
    
    // 특정 gcode에 대한 stock 데이터 조회
    @Query("SELECT s FROM Stock s JOIN FETCH s.goods WHERE s.goods.gcode = :gcode")
    List<Stock> findByGcode(String gcode);
}
