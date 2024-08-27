package com.exam.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.exam.dto.GoodsDTO;
import com.exam.dto.MovementDTO;
import com.exam.dto.MovementGoodsDTO;
import com.exam.entity.Goods;
import com.exam.entity.Movement;
import com.exam.repository.MovementRepository;

@Service
@Transactional
public class MovementServiceImpl implements MovementService {
    
    private static final Logger logger = LoggerFactory.getLogger(MovementServiceImpl.class);

    private final MovementRepository movementRepository;
    private final ModelMapper mapper = new ModelMapper();

    public MovementServiceImpl(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    @Override
    public List<MovementDTO> findAll(){
        logger.debug("Request to find all movements");
        ModelMapper mapper = new ModelMapper();
        
        List<Movement> list = movementRepository.findAll();
        List<MovementDTO> movementList = list.stream()
                                              .map(e->mapper.map(e, MovementDTO.class))
                                              .collect(Collectors.toList());
        logger.debug("Movements found: {}", movementList);
        return movementList;
    }


    @Override
    public List<MovementDTO> findGroupedByMovdate(String branchid) {
        logger.debug("Request to find movements grouped by date");
        List<Object[]> results = movementRepository.findGroupedByMovdate(branchid);

        List<MovementDTO> groupedMovements = results.stream()
                                                    .map(result -> MovementDTO.builder()
                                                                              .movdate((LocalDate) result[0])
                                                                              .ordercode((String) result[1])
                                                                              .count((Long) result[2])
                                                                              .build())
                                                    .collect(Collectors.toList());
        logger.debug("Grouped movements found: {}", groupedMovements);
        return groupedMovements;
    }

	
	@Override
	public List<MovementDTO> findAllByOrderByMovdateDesc(){
		ModelMapper mapper = new ModelMapper();
		
		List<Movement> list = movementRepository.findAllByOrderByMovdateDesc();
		List<MovementDTO> movementList = list.stream()
				 							 .map(e->mapper.map(e, MovementDTO.class))
				 							 .collect(Collectors.toList());
		return movementList;
	}
	

    // 날짜순으로 모든 데이터찾기
    @Override
    public List<MovementDTO> findAllSortedByDate() {
        logger.debug("Request to find all movements sorted by date");
        ModelMapper mapper = new ModelMapper();
        
        List<Movement> list = movementRepository.findAllByOrderByMovdateAsc();
        List<MovementDTO> movementList = list.stream()
                                              .map(e -> mapper.map(e, MovementDTO.class))
                                              .collect(Collectors.toList());
        logger.debug("Sorted movements found: {}", movementList);
        return movementList;
    }

    // 날짜별로 데이터를 그룹화하여 반환
    @Override
    public Map<LocalDate, List<MovementDTO>> findAllGroupedByDate() {
        List<Movement> movements = movementRepository.findAll();
        ModelMapper mapper = new ModelMapper();
        
        List<MovementDTO> movementDTOs = movements.stream()
                .map(e -> mapper.map(e, MovementDTO.class))
                .collect(Collectors.toList());

        return movementDTOs.stream()
                .collect(Collectors.groupingBy(MovementDTO::getMovdate));
    }
    
    @Override
    public List<MovementDTO> findByMovdate(String branchid, LocalDate movdate) {
        logger.debug("Request to find movements by movdate: {}", movdate);
        ModelMapper mapper = new ModelMapper();
        
        List<Movement> list = movementRepository.findByMovdate(branchid, movdate);
        List<MovementDTO> movementList = list.stream()
                                              .map(e -> mapper.map(e, MovementDTO.class))
                                              .collect(Collectors.toList());
        logger.debug("Movements found for movdate {}: {}", movdate, movementList);
        return movementList;
    }
    
    @Override
    public List<MovementGoodsDTO> findMovementsWithGoodsByMovdate(String branchid, LocalDate movdate) {
        ModelMapper mapper = new ModelMapper();
        List<Object[]> results = movementRepository.findMovementsWithGoodsByMovdate(branchid, movdate);
        List<MovementGoodsDTO> movementGoodsList = results.stream()
                .map(result -> {
                    Movement movement = (Movement) result[0];
                    Goods goods = (Goods) result[1];
                    MovementDTO movementDTO = mapper.map(movement, MovementDTO.class);
                    GoodsDTO goodsDTO = mapper.map(goods, GoodsDTO.class);
                    return new MovementGoodsDTO(movementDTO, goodsDTO);
                })
                .collect(Collectors.toList());
        return movementGoodsList;
    }
   

    // 모바일 - gcode 로 데이터 조회
    @Override
    public List<MovementDTO> findByGcode(String gcode){
        logger.debug("gcode로 이동 데이터를 조회하는 요청: {}", gcode);
        List<Movement> list = movementRepository.findByGcode(gcode);
        return list.stream()
                   .map(e -> mapper.map(e, MovementDTO.class))
                   .collect(Collectors.toList());
    }

    // 모바일 - status 변경 (대기 -> 완료)
    @Override
    public void updateMovStatusByBranchIdAndGcode(String branchid, String gcode, LocalDate movdate, String newStatus) {
        System.out.printf("Updating movstatus for branchid: {}, gcode: {}, movdate: {} to newStatus: {}", branchid, gcode, movdate, newStatus);
        movementRepository.updateMovStatusByBranchIdAndGcode(branchid, gcode, movdate, newStatus);
    }


	
	// 모바일 - branchId 와 movdate 로 데이터 조회
    @Override
    public List<MovementDTO> findByBranchIdAndMovdate(String branchid, LocalDate movdate) {
        logger.debug("Request to find movements by branchid: {} and movdate: {}", branchid, movdate);
        List<Movement> movements = movementRepository.findByMovdate(branchid, movdate);
        return movements.stream()
                        .map(movement -> mapper.map(movement, MovementDTO.class))
                        .collect(Collectors.toList());
    }
	
	
	//admin
	@Override
    public List<Object[]> findBranchMovements() {
        return movementRepository.findBranchMovements();
    }
    
    @Override
    public void updateMovstatusForGroup(String branchName, LocalDate movdate, String movstatus) {
        movementRepository.updateMovstatusForGroup(branchName, movdate, movstatus);
    }

	@Override
	public List<Movement> OrderSaveMovements(List<MovementDTO> movementDTOs) {
	    List<Movement> movements = new ArrayList<>();
	    for (MovementDTO dto : movementDTOs) {
	        Movement movement = new Movement();
	        movement.setOrdernum(dto.getOrdernum());
	        movement.setBranchid(dto.getBranchid());
	        movement.setGcode(dto.getGcode());
	        movement.setMovquantity(dto.getMovquantity()); // gcount -> movquantity
	        movement.setMovdate(dto.getMovdate()); // 프론트에서 받아온 movdate
	        movement.setMovstatus(dto.getMovstatus()); // 프론트에서 받아온 movstatus
	        movement.setOrdercode(dto.getOrdercode());
	        movements.add(movement);
	    }
	    return movementRepository.saveAll(movements);
	}
	
//	@Override
//	public List<MovementDTO> findByOrdercode(String ordercode){
//		ModelMapper mapper = new ModelMapper();
//		
//		List<Movement> list = movementRepository.findByOrdercode(ordercode);
//		List<MovementDTO> movementList = list.stream()
//				 							 .map(e->mapper.map(e, MovementDTO.class))
//				 							 .collect(Collectors.toList());
//		return movementList;
//	}

    @Override
    public List<MovementGoodsDTO> findPendingMovementsByBranchAndDate(String branchName, LocalDate movdate, String movstatus) {
        logger.debug("Request to find pending movements for branch: {} and date: {} with movstatus: {}", branchName, movdate, movstatus);
        List<Object[]> results = movementRepository.findPendingMovementsByBranchAndDate(branchName, movdate, movstatus);
        return results.stream()
                      .map(result -> {
                          Movement movement = (Movement) result[0];
                          Goods goods = (Goods) result[1];
                          return new MovementGoodsDTO(mapper.map(movement, MovementDTO.class),
                                                      mapper.map(goods, GoodsDTO.class));
                      })
                      .collect(Collectors.toList());
    }

 // branchId 기준으로 movstatus 가 반려인 데이터 조회
    @Override
    public List<MovementDTO> findRejectedMovementsByBranchId(String branchid) {
        List<Movement> rejectedMovements = movementRepository.findByBranchIdAndMovstatusRejected(branchid);
        return rejectedMovements.stream()
                                .map(movement -> mapper.map(movement, MovementDTO.class))
                                .collect(Collectors.toList());
    }


}
