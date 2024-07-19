package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.EmpDTO;
import com.exam.entity.Emp;
import com.exam.repository.GoodsRepository;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
	
	GoodsRepository goodsRepository;

	public GoodsServiceImpl(GoodsRepository goodsRepository) {
		this.goodsRepository = goodsRepository;
	}
	
	


	

}
