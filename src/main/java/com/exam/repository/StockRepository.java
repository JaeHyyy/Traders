package com.exam.repository;


import java.sql.Date;
import java.util.List;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.dto.GnameSummaryDTO;
import com.exam.dto.StockDTO;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.Goods;
import com.exam.entity.Movement;
import com.exam.entity.OrderCart;
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

	// 모바일 - 상세정보페이지 위치 및 가격 업데이트
	@Modifying
	@Transactional
	@Query("UPDATE Stock s SET s.loc1 = :loc1, s.loc2 = :loc2, s.loc3 = :loc3, s.gprice = (SELECT g.gcostprice FROM Goods g WHERE g.gcode = :gcode) WHERE s.goods.gcode = :gcode AND s.user.branchId = :branchId")
	void mobileUpdateLocationByGcodeAndBranchId(@Param("gcode") String gcode, 
	                                            @Param("branchId") String branchId, 
	                                            @Param("loc1") String loc1, 
	                                            @Param("loc2") String loc2, 
	                                            @Param("loc3") String loc3);



	@Modifying
	@Transactional
	@Query("UPDATE Stock s SET s.loc1 = :loc1, s.loc2 = :loc2, s.loc3 = :loc3, s.gprice = :price WHERE s.goods.gcode = :gcode AND s.user.branchId = :branchId")
	void updateLocationWithPrice(@Param("gcode") String gcode, 
	                             @Param("branchId") String branchId, 
	                             @Param("loc1") String loc1, 
	                             @Param("loc2") String loc2, 
	                             @Param("loc3") String loc3, 
	                             @Param("price") Integer price);






    
    //관리자 메인화면 지점 순위 막대그래프
    @Query("SELECT s.user.branchId, s.user.branchName, count(s) FROM Stock s GROUP BY s.user.branchId")
    List<Object[]> countStocksByBranch();

    // branchId 로 Stock 조회
    // 유통기한 안 지난 재고 상품들만 branchid를 기준으로 가져오기
    @Query("SELECT s FROM Stock s WHERE s.expdate >= :currentDate AND s.user.branchId = :branchId")
    List<Stock> findByBranchIdStock(@Param("currentDate") LocalDate currentDate, @Param("branchId") String branchId);
 
    //관리자 메인화면 상품순위 도넛그래프
    @Query("SELECT g.gname, SUM(s.stockquantity) as summ FROM Stock s JOIN s.goods g GROUP BY g.gname order by summ desc")
    List<Object[]> findGnameSummaries();
    
    //검색창 입력값으로 stock조회
    @Query("SELECT s FROM Stock s WHERE s.user.branchId = :branchId " +
    	       "AND (s.goods.gcode LIKE :keyword " + // 상품코드 검색
    	       "OR s.goods.gname LIKE :keyword " +   // 상품 이름 검색
    	       "OR s.goods.gcategory LIKE :keyword " + // 카테고리 검색
    	       "OR s.goods.gunit LIKE :keyword " + // 단위 검색
    	       "OR s.loc1 LIKE :keyword " +          // loc1 검색
    	       "OR s.loc2 LIKE :keyword " +          // loc2 검색
    	       "OR s.loc3 LIKE :keyword " +          // loc3 검색
    	       "OR CAST(s.gprice AS string) LIKE :keyword " + // 가격 검색
    	       "OR CAST(s.stockquantity AS string) LIKE :keyword " + // 재고량 검색
    	       "OR CAST(s.stockid AS string) LIKE :keyword " + // 재고코드 검색
    	       "OR CAST(s.expdate AS string) LIKE :keyword) " + // 유통기한 검색
    	       "AND s.expdate >= :currentDate")
    	List<Stock> stockSearchByKeyword(@Param("currentDate") LocalDate currentDate,
    	                                 @Param("branchId") String branchId,
    	                                 @Param("keyword") String keyword);
    
    
    ////////////////////////////////////////////////////////////////////////////////
    
    // branchId 로 stock 테이블 + goods 테이블 데이터 조회
 	@Query("SELECT s FROM Stock s JOIN FETCH s.goods WHERE s.user.branchId = :branchId AND s.expdate >= :currentDate")
 	List<Stock> findAllWithGoodsByBranchId(@Param("branchId") String branchId, @Param("currentDate") LocalDate currentDate);

    
}
