package com.exam.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.DisUse;
import com.exam.entity.Stock;





public interface DisUseRepository extends JpaRepository<DisUse, Integer> {

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
	
	
	// disuse 테이블에 이미 존재하는지 확인(중복 저장 방지)
	boolean existsByStock(Stock stock);
	
	@Query("SELECT d FROM DisUse d WHERE d.user.branchId = :branchId")
	List<DisUse> findByBranchIdDisuse(@Param("branchId") String branchId);
	
	
//	
//	@Query("SELECT d.stock.goods.gcode FROM DisUse d WHERE d.stock.goods.gcode IN :gcodes AND d.user.branchId = :branchId")
//	List<String> findDuplicateGcodes(@Param("gcodes") List<String> gcodes, @Param("branchId") String branchId);

	
}
