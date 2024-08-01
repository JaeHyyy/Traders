package com.exam.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.exam.dto.GoodsDTO;
import com.exam.entity.Goods;


public interface GoodsService {

	public List<GoodsDTO> findAll();
	public List<GoodsDTO> search(String keyword);
	public Goods save(GoodsDTO goodsDTO, MultipartFile file);
	
	public List<GoodsDTO> findByGcode(String gcode);
}
