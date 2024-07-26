package com.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {

	List<Movement> findByOrdercode(Long ordercode);
	
	@Query("SELECT m.movdate, count(m) FROM Movement m GROUP BY m.movdate ORDER BY m.movdate")
    List<Object[]> findGroupedByMovdate();
    
    List<Movement> findAllByOrderByMovdateAsc(); // 날짜순으로 모든 이동 데이터를 찾기 위한 새로운 메서드
    List<Movement> findAllByOrderByMovdateDesc();
    
}
