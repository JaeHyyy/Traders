package com.exam.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Param;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.MovementDTO;
import com.exam.entity.Movement;
import com.exam.repository.MovementRepository;

@Service
@Transactional
public class MovementServiceImpl implements MovementService {
	
	MovementRepository movementRepository;

	public MovementServiceImpl(MovementRepository movementRepository) {
		this.movementRepository = movementRepository;
	}
	
	@Override
	public List<MovementDTO> findAll(){
		ModelMapper mapper = new ModelMapper();
		
		List<Movement> list = movementRepository.findAll();
		List<MovementDTO> movementList = list.stream()
				 							 .map(e->mapper.map(e, MovementDTO.class))
				 							 .collect(Collectors.toList());
		return movementList;
	}
	
	@Override
	public List<MovementDTO> findByOrdercode(Long ordercode){
		ModelMapper mapper = new ModelMapper();
		
		List<Movement> list = movementRepository.findByOrdercode(ordercode);
		List<MovementDTO> movementList = list.stream()
				 							 .map(e->mapper.map(e, MovementDTO.class))
				 							 .collect(Collectors.toList());
		return movementList;
	}
	
	@Override
    public List<MovementDTO> findGroupedByMovdate() {
        List<Object[]> results = movementRepository.findGroupedByMovdate();

        return results.stream()
                      .map(result -> MovementDTO.builder()
                    		  					.movdate((LocalDate) result[0])
                    		  					.count((Long) result[1])
                                                .build())
                      .collect(Collectors.toList());
    }
	
	@Override
	public List<MovementDTO> findAllByOrderByMovdateAsc(){
		ModelMapper mapper = new ModelMapper();
		
		List<Movement> list = movementRepository.findAllByOrderByMovdateAsc();
		List<MovementDTO> movementList = list.stream()
				 							 .map(e->mapper.map(e, MovementDTO.class))
				 							 .collect(Collectors.toList());
		return movementList;
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
	
}
