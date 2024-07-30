package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exam.dto.GoodsDTO;
import com.exam.dto.OrderCartDTO;
import com.exam.entity.Goods;
import com.exam.entity.OrderCart;
import com.exam.repository.OrderCartRepository;

@Service
@Transactional
public class OrderCartServiceImpl implements OrderCartService {
	
	OrderCartRepository orderCartRepository;

	public OrderCartServiceImpl(OrderCartRepository orderCartRepository) {
		this.orderCartRepository = orderCartRepository;
	}

	@Override
	public List<OrderCartDTO> findAll() {
		 ModelMapper mapper = new ModelMapper();
			
			List<OrderCart> list = orderCartRepository.findAll();
			
			List<OrderCartDTO> ordercartList = list.stream()
					.map(e->mapper.map(e, OrderCartDTO.class))
					.collect(Collectors.toList());
			return ordercartList;
		
	}


    @Override
    public void saveAll(List<OrderCartDTO> dtos) {
        ModelMapper mapper = new ModelMapper();
        // 추가된 로그
        System.out.println("Received OrderCartDTOs: " + dtos);
        List<OrderCart> orderCarts = dtos.stream()
        		                 .map(dto -> {
                     // 매핑 전 dto 로그 출력
                     System.out.println("Mapping OrderCartDTO: " + dto);
                     OrderCart orderCart = mapper.map(dto, OrderCart.class);
                     // 매핑 후 orderCart 로그 출력
                     System.out.println("Mapped OrderCart entity: " + orderCart);
                     return orderCart;
                 })
                                 .collect(Collectors.toList());
        orderCartRepository.saveAll(orderCarts);	
        System.out.println("Saved OrderCarts: " + orderCarts);
    }
	



//	@Override
//	public void saveOrderCart(List<OrderCartDTO> orderCartDTO) {
//		List<OrderCart> orderCarts = orderCartDTO.stream()
//	            .map(dto -> {
//	                OrderCart orderCart = new OrderCart();
//	                orderCart.setGcount(dto.getGcount());
//	                orderCart.setGoods(dto.getGoods());
//	                return orderCart;
//	            }).collect(Collectors.toList());
//	        orderCartRepository.saveAll(orderCarts);
//	    }
		

}