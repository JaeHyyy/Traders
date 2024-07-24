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
	@Override
	public List<GoodsDTO> search(String keyword) {
		ModelMapper mapper = new ModelMapper();
		List<Goods> list = goodsRepository.search(keyword);
		
		List<GoodsDTO> goodsList = list.stream()
				.map(e->mapper.map(e, GoodsDTO.class))
				.collect(Collectors.toList());
		return goodsList;
	}
	
    private GoodsDTO convertToDTO(Goods goods) {
        GoodsDTO dto = new GoodsDTO();
        dto.setGcode(goods.getGcode());
        dto.setGcategory(goods.getGcategory());
        dto.setGname(goods.getGname());
        dto.setGcostprice(goods.getGcostprice());
        return dto;
    }
	
	
	
	


	

}
