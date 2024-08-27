package com.exam.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.exam.dto.GoodsDTO;
import com.exam.entity.Goods;


public interface GoodsService {

	public List<GoodsDTO> findAll();
	public List<GoodsDTO> search(String keyword);
	public Goods save(GoodsDTO goodsDTO, MultipartFile file);
	

	// 모바일용 데이터 - 전체조회
	public List<GoodsDTO> findAllData();
	// 모바일용 데이터 - gcode 기준으로 찾기
	GoodsDTO findByGcode(String gcode);
	
	//본사 상품 추가
	GoodsDTO addGoods(GoodsDTO goodsDTO);

}
