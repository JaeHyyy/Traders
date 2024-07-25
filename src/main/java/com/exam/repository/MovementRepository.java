package com.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {

	List<Movement> findByOrdercode(Long ordercode);
	
	@Query("SELECT m.movdate, count(m) FROM Movement m GROUP BY m.movdate")
    List<Object[]> findGroupedByMovdate();

    
}
