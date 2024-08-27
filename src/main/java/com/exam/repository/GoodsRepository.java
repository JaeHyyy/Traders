package com.exam.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.Goods;



public interface GoodsRepository extends JpaRepository<Goods, String> {

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
	
    //입력값으로 해당 상품 조회
	@Query("SELECT g FROM Goods g WHERE g.gname LIKE %:keyword% OR g.gcategory LIKE %:keyword% OR g.gcode LIKE %:keyword% OR CAST(g.gcostprice AS string) LIKE %:keyword%")
	List<Goods> search (@Param("keyword") String keyword);
	

	// 모바일용 - gcode 기준으로 데이터 찾기
	Goods findByGcode(String gcode);
	
    @Query("SELECT g.gcostprice FROM Goods g WHERE g.gcode = :gcode")
    Integer findGcostpriceByGcode(String gcode);



}
