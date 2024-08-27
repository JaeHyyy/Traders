package com.exam.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.dto.OrderCartDTO;
import com.exam.entity.DisUse;
import com.exam.entity.OrderCart;
import com.exam.entity.User;
import com.exam.repository.OrderCartRepository;
import com.exam.repository.UserRepository;

@Service
@Transactional
public class OrderCartServiceImpl implements OrderCartService {
	
	OrderCartRepository orderCartRepository;
	
	@Autowired
	UserRepository userRepository;
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


//	//메인에서 발주하기 눌렀을 때 ordercart db테이블에 해당 상품 저장 
//    @Override
//    public void saveAll(List<OrderCartDTO> dtos) {
//        ModelMapper mapper = new ModelMapper();
//        // 받아온 dto 출력
//        System.out.println("Received OrderCartDTOs: " + dtos);
//        List<OrderCart> orderCarts = dtos.stream()
//        		                 .map(dto -> {
//                     // 매핑 전 dto 출력
//                     System.out.println("Mapping OrderCartDTO: " + dto);
//                     OrderCart orderCart = mapper.map(dto, OrderCart.class);
//                     // 매핑 후 orderCartㄴ 출력
//                     System.out.println("Mapped OrderCart entity: " + orderCart);
//                     return orderCart;
//                 })
//                                 .collect(Collectors.toList());
//        orderCartRepository.saveAll(orderCarts);	
//        System.out.println("Saved OrderCarts: " + orderCarts);
//    }
//
//    
//     //발주하기 페이지에서 선택 후 담았던 상품 삭제 
//	@Override
//	public void delete(int ordercode) {
//		OrderCart orderCart = orderCartRepository.findById(ordercode).orElse(null);
//		if(orderCart!=null) {
//			orderCartRepository.delete(orderCart);
//		}
//		
//	}
//
//	
//	//발주하기 페이지에서 수량 변경 후 수정 내용 변경하기
//	@Override
//	public void update(int ordercode, OrderCartDTO dto) {
//		//우선 찾기 먼저 하고
//		OrderCart orderCart = orderCartRepository.findById(ordercode).orElse(null);
//		//더티체킹(수정)
//		if (orderCart != null) {
//		orderCart.setGcount(dto.getGcount());
//		orderCartRepository.save(orderCart);
//		}
//	}
	
	// ▼ security 적용 후
	// branchId 로 OrderCart 조회
    @Override
    public List<OrderCartDTO> findByBranchId(String branchId) {
        List<OrderCart> list = orderCartRepository.findByBranchId(branchId);
        return list.stream().map(e -> mapper.map(e, OrderCartDTO.class)).collect(Collectors.toList());
    }
	
    @Override
    public void saveAll(String branchId, List<OrderCartDTO> dtos) {
        User user = userRepository.findByBranchId(branchId);
        if (user != null) {
            List<OrderCart> orderCarts = dtos.stream()
                                             .map(dto -> {
                                                 OrderCart orderCart = mapper.map(dto, OrderCart.class);
                                                 // ordercode 수동 생성
//                                                 orderCart.setOrdercode(UUID.randomUUID().toString());
                                                 orderCart.setUser(user);
                                                 return orderCart;
                                             })
                                             .collect(Collectors.toList());
//            System.out.println(">>>>>>>>>>>>>>>>>" + orderCarts);
            orderCartRepository.saveAll(orderCarts);
        }
    }
    
    // 결제성공시 상품 삭제
    @Override
    public void delete(String branchId, String ordercode) {
        List<OrderCart> orderCarts = orderCartRepository.findAllByOrdercodeAndUserBranchId(ordercode, branchId);
        if (!orderCarts.isEmpty()) {
            orderCartRepository.deleteAll(orderCarts);
        } else {
            throw new EntityNotFoundException("OrderCart not found for ordercode: " + ordercode + " and branchId: " + branchId);
        }
    }
    
    // branchId와 gcode로 수량 업데이트
    @Override
    public void updateByGcode(String branchId, String gcode, OrderCartDTO dto) {
        OrderCart orderCart = orderCartRepository.findByBranchIdAndGcode(branchId, gcode);
        if (orderCart != null) {
            orderCart.setGcount(dto.getGcount());
            orderCartRepository.save(orderCart);
        }
    }
    

    @Override
    public void update(String branchId, String ordercode, OrderCartDTO dto) {
        OrderCart orderCart = orderCartRepository.findByIdAndUserBranchId(ordercode, branchId);
        if (orderCart != null) {
            orderCart.setGcount(dto.getGcount());
            orderCartRepository.save(orderCart);
        }
    }

    
    //발주하기 페이지에서 선택한 상품 삭제
    @Transactional
    @Override
    public void selectedDelete(String branchId, String gcode) {
        OrderCart orderCart2 = orderCartRepository.findByBranchIdAndGcode(branchId, gcode);
        System.out.println(orderCart2);
        if(orderCart2 != null) {
            orderCartRepository.delete(orderCart2);
            System.out.println("OrderCart 삭제 완료: " + gcode);
        } else {
            System.out.println("OrderCart를 찾을 수 없습니다: gcode = " + gcode + ", branchId = " + branchId);
        }
    }

}