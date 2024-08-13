package com.exam.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.exam.dto.MovementDTO;
import com.exam.dto.MovementGoodsDTO;


public interface MovementService {
	
	public List<MovementDTO> findAll();
	public List<MovementDTO> findByMovdate(String branchid, LocalDate movdate);
	public List<MovementDTO> findGroupedByMovdate(String branchid);
	public List<MovementDTO> findAllByOrderByMovdateDesc();
	
	// 날짜순으로 모든 데이터찾기
	public List<MovementDTO> findAllSortedByDate();
    // 날짜별로 데이터를 그룹화하여 반환
    Map<LocalDate, List<MovementDTO>> findAllGroupedByDate();

    // 모바일 - gcode 로 데이터 조회
    List<MovementDTO> findByGcode(String gcode);
    // 모바일 - status 변경 (대기 -> 완료)
    void updateMovStatus(Long movidx, String newStatus);

    
    public List<MovementGoodsDTO> findMovementsWithGoodsByMovdate(String branchid, LocalDate movdate);
    
    //admin page
    public List<Object[]> findBranchMovements();
    
    //admin 출고상세
    void updateMovstatusForGroup(String branchName, LocalDate movdate, String movstatus);
    
 // 출고 대기 상태인 Movement와 Goods 조회
    List<MovementGoodsDTO> findPendingMovementsByBranchAndDate(String branchName, LocalDate movdate, String movstatus);
    

}
