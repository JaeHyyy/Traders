package com.exam.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {

	List<Movement> findByOrdercode(Long ordercode);
	@Query("SELECT m FROM Movement m WHERE m.branchid = :branchid  GROUP BY m.movdate")
	    List<Movement> findAllByBranchIdGroupedByDate(@Param("branchid") String branchid);

}
