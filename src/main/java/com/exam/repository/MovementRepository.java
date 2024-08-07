package com.exam.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.exam.dto.MovementGoodsDTO;
import com.exam.entity.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {
	
	@Query("SELECT m FROM Movement m WHERE m.branchid = :branchid AND m.movdate = :movdate")
	List<Movement> findByMovdate(@Param("branchid") String branchid, LocalDate movdate);
	
	@Query("SELECT m.movdate, count(m) FROM Movement m WHERE m.branchid = :branchid GROUP BY m.movdate order by m.movdate desc")
    List<Object[]> findGroupedByMovdate(@Param("branchid") String branchid);
    
    List<Movement> findAllByOrderByMovdateAsc(); // 날짜순으로 모든 이동 데이터를 찾기 위한 새로운 메서드
    List<Movement> findAllByOrderByMovdateDesc();
    

    // 모바일 - gcode 로 데이터 조회
    List<Movement> findByGcode(String gcode);
    // 모바일 - 상태업데이트 ( 대기 -> 완료 )
    @Modifying
    @Query("UPDATE Movement m SET m.movstatus = :newStatus WHERE m.movidx = :movidx")
    void updateMovStatus(@Param("movidx") Long movidx, @Param("newStatus") String newStatus);

    @Query("SELECT m, g FROM Movement m JOIN Goods g ON m.gcode = g.gcode WHERE m.branchid = :branchid AND m.movdate = :movdate")
    List<Object[]> findMovementsWithGoodsByMovdate(@Param("branchid") String branchid, @Param("movdate") LocalDate movdate);
    
}
