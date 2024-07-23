package com.exam.service;

import java.util.List;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.GoodsDTO;
import com.exam.entity.Goods;
import com.exam.repository.GoodsRepository;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
	
	GoodsRepository goodsRepository;

	public GoodsServiceImpl(GoodsRepository goodsRepository) {
		this.goodsRepository = goodsRepository;
	}

	//본사 상품 전체 조회
	@Override
	public List<GoodsDTO> findAll() {
        ModelMapper mapper = new ModelMapper();
		
		List <Goods> list = goodsRepository.findAll();
		
		List<GoodsDTO> goodsList = list.stream()
				.map(e->mapper.map(e, GoodsDTO.class))
				.collect(Collectors.toList());
		return goodsList;
	}
	
	//상품명, 카테고리명, 상품코드로 검색 조회
	//본사 상품의 상품명 검색으로 해당 상품 조회
	@Override
	public List<GoodsDTO> findByGname(String gname) {
		ModelMapper mapper = new ModelMapper();
		List<Goods> list = goodsRepository.findByGnameContaining(gname);
		
		List<GoodsDTO> goodsList = list.stream()
				.map(e->mapper.map(e, GoodsDTO.class))
				.collect(Collectors.toList());
		return goodsList;
	}
	//본사 상품의 카테고리명 검색으로 해당 상품 조회
	@Override
	public List<GoodsDTO> findByGcategory(String gcategory) {
		ModelMapper mapper = new ModelMapper();
		List<Goods> list = goodsRepository.findByGcategoryContaining(gcategory);
		
		List<GoodsDTO> goodsList = list.stream()
				.map(e->mapper.map(e, GoodsDTO.class))
				.collect(Collectors.toList());
		return goodsList;
	}
	
	
	
	
	


	

}
