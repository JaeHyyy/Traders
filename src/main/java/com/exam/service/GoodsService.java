package com.exam.service;

import java.util.List;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.exam.dto.GoodsDTO;


public interface GoodsService {

	public List<GoodsDTO> findAll();
	public List<GoodsDTO> findByGname( String gname);
}
