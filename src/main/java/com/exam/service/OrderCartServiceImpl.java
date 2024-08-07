package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.OrderCartDTO;
import com.exam.entity.OrderCart;
import com.exam.repository.OrderCartRepository;

@Service
@Transactional
public class OrderCartServiceImpl implements OrderCartService {
	
	OrderCartRepository orderCartRepository;
    ModelMapper mapper = new ModelMapper();

	public OrderCartServiceImpl(OrderCartRepository orderCartRepository) {
		this.orderCartRepository = orderCartRepository;
	}

	//메인페이지에서 본사 상품 전체 조회	
	@Override
	public List<OrderCartDTO> findAll() {
		 ModelMapper mapper = new ModelMapper();
			
			List<OrderCart> list = orderCartRepository.findAll();
			
			List<OrderCartDTO> ordercartList = list.stream()
					.map(e->mapper.map(e, OrderCartDTO.class))
					.collect(Collectors.toList());
			return ordercartList;
		
	}


	//메인에서 발주하기 눌렀을 때 ordercart db테이블에 해당 상품 저장 
    @Override
    public void saveAll(List<OrderCartDTO> dtos) {
        ModelMapper mapper = new ModelMapper();
        // 받아온 dto 출력
        System.out.println("Received OrderCartDTOs: " + dtos);
        List<OrderCart> orderCarts = dtos.stream()
        		                 .map(dto -> {
                     // 매핑 전 dto 출력
                     System.out.println("Mapping OrderCartDTO: " + dto);
                     OrderCart orderCart = mapper.map(dto, OrderCart.class);
                     // 매핑 후 orderCartㄴ 출력
                     System.out.println("Mapped OrderCart entity: " + orderCart);
                     return orderCart;
                 })
                                 .collect(Collectors.toList());
        orderCartRepository.saveAll(orderCarts);	
        System.out.println("Saved OrderCarts: " + orderCarts);
    }

    
     //발주하기 페이지에서 선택 후 담았던 상품 삭제 
	@Override
	public void delete(int ordercode) {
		OrderCart orderCart = orderCartRepository.findById(ordercode).orElse(null);
		if(orderCart!=null) {
			orderCartRepository.delete(orderCart);
		}
		
	}

	
	//발주하기 페이지에서 수량 변경 후 수정 내용 변경하기
	@Override
	public void update(int ordercode, OrderCartDTO dto) {
		//우선 찾기 먼저 하고
		OrderCart orderCart = orderCartRepository.findById(ordercode).orElse(null);
		//더티체킹(수정)
		if (orderCart != null) {
		orderCart.setGcount(dto.getGcount());
		orderCartRepository.save(orderCart);
		}
	}
	
	// branchId 로 OrderCart 조회
    @Override
    public List<OrderCartDTO> findByBranchId(String branchId) {
        List<OrderCart> list = orderCartRepository.findByBranchId(branchId);
        return list.stream().map(e -> mapper.map(e, OrderCartDTO.class)).collect(Collectors.toList());
    }
	
	
	
	





}