package com.exam.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.exam.dto.OrderCartDTO;
import com.exam.entity.OrderCart;





public interface OrderCartRepository extends JpaRepository<OrderCart, Integer> {

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
	
	// branchId 로 OrderCart 조회
//    @Query("SELECT o FROM ordercart o JOIN o.user u WHERE u.branchId = :branchId")
    @Query("SELECT o FROM OrderCart o JOIN o.user u WHERE u.branchId = :branchId")
    List<OrderCart> findByBranchId(@Param("branchId") String branchId);
    
    // 웹 - branchId 로 OrderCart 조회 
    @Query("SELECT o FROM OrderCart o JOIN o.user u WHERE o.ordercode = :ordercode AND u.branchId = :branchId")
    OrderCart findByIdAndUserBranchId(@Param("ordercode") int ordercode, @Param("branchId") String branchId);

	
}
