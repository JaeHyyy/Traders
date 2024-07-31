package com.exam.repository;

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
	
	List<Stock> findByGoodsGcode(String gcode);
}
