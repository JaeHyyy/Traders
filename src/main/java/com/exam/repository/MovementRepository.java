package com.exam.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {

	@Query("SELECT m FROM Movement m WHERE m.branchid = :branchid and m.movstatus = '출고 완료' AND m.movdate = :movdate")
	List<Movement> findByMovdate(@Param("branchid") String branchid, LocalDate movdate);
	//입고페이지 - 지점명+날짜별로 그룹화하여 상태가 출고완료인것만 건수 표기
	@Query("SELECT m.movdate, m.ordercode, count(m) FROM Movement m WHERE m.branchid = :branchid and m.movstatus = '출고 완료' GROUP BY m.movdate, m.ordercode order by m.movdate desc")
	List<Object[]> findGroupedByMovdate(@Param("branchid") String branchid);

	List<Movement> findAllByOrderByMovdateAsc(); // 날짜순으로 모든 이동 데이터를 찾기 위한 새로운 메서드

	List<Movement> findAllByOrderByMovdateDesc();

	// 모바일 - gcode 로 데이터 조회
	List<Movement> findByGcode(String gcode);

	// 모바일 - 상태업데이트 ( 대기 -> 완료 )
	@Modifying
	@Query("UPDATE Movement m SET m.movstatus = :newStatus WHERE m.branchid = :branchid AND m.gcode = :gcode AND m.movdate = :movdate")
	void updateMovStatusByBranchIdAndGcode(@Param("branchid") String branchid, @Param("gcode") String gcode, @Param("movdate") LocalDate movdate, @Param("newStatus") String newStatus);



//	@Modifying
//	@Query("INSERT INTO Movement (ordercode, gcode, branchid, movdate, movquantity, movstatus) "
//			+ "VALUES (:ordercode, :gcode, :branchid, :movdate, :movquantity, :movstatus)")
//	void insertMovement(@Param("ordercode") String ordercode,@Param("gcode") String gcode,@Param("branchid") String branchid,@Param("movquantity") Long movquantity);
	
    @Query("SELECT m, g FROM Movement m JOIN Goods g ON m.gcode = g.gcode WHERE m.branchid = :branchid AND m.movdate = :movdate")
    List<Object[]> findMovementsWithGoodsByMovdate(@Param("branchid") String branchid, @Param("movdate") LocalDate movdate);
    
    
    //admin page 출고관리 페이지 - 출고대기상품 조회
    @Query("select u.branchName, m.movdate, count(m) from User u JOIN Movement m ON u.branchId = m.branchid where m.movstatus = '출고 대기' group by u.branchName, m.movdate")
    List<Object[]> findBranchMovements();
    
    @Modifying
    @Transactional //선택한 지점+날짜 에 해당하는 데이터의 상태변경
    @Query("UPDATE Movement m SET m.movstatus = :movstatus WHERE m.branchid = (SELECT u.branchId FROM User u WHERE u.branchName = :branchName) AND m.movdate = :movdate")
    void updateMovstatusForGroup(@Param("branchName") String branchName, @Param("movdate") LocalDate movdate, @Param("movstatus") String movstatus);
    // 선택한 지점+날짜 에 해당하는 데이터들의 개별 상태조회
    @Query("SELECT m, g FROM Movement m JOIN Goods g ON m.gcode = g.gcode WHERE m.branchid = (SELECT u.branchId FROM User u WHERE u.branchName = :branchName) AND m.movdate = :movdate AND m.movstatus=:movstatus ")
    List<Object[]> findPendingMovementsByBranchAndDate(@Param("branchName") String branchName, @Param("movdate") LocalDate movdate, @Param("movstatus") String movstatus);
    
//    @Modifying
//    @Transactional
//    @Query("UPDATE Movement m Set m.movdate = :movstatus WHERE ")
    
    // branchId 기준으로 movstatus 가 반려인 데이터 조회
    @Query("SELECT m FROM Movement m WHERE m.branchid = :branchid AND m.movstatus = '반려'")
    List<Movement> findByBranchIdAndMovstatusRejected(@Param("branchid") String branchid);

    
}
