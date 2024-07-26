package com.exam.service;

import java.util.List;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.exam.dto.GoodsDTO;
import com.exam.dto.OrderCartDTO;
import com.exam.entity.Goods;


public interface OrderCartService {

	public List<OrderCartDTO> findAll();

}
