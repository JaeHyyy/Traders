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



	
	
	


	

}
