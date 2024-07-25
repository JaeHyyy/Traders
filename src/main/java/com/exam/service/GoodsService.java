package com.exam.service;

import java.util.List;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.exam.dto.GoodsDTO;
import com.exam.entity.Goods;


public interface GoodsService {

	public List<GoodsDTO> findAll();
	public List<GoodsDTO> search(String keyword);
}
