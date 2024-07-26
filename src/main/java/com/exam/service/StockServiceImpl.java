package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.StockDTO;
import com.exam.entity.Stock;
import com.exam.repository.StockRepository;

@Service
@Transactional
public class StockServiceImpl implements StockService{
	
	StockRepository stockRepository;

	public StockServiceImpl(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}
	
	@Override
	public List<StockDTO> findAll(){
		ModelMapper mapper = new ModelMapper();
		
		List<Stock> list = stockRepository.findAll();
		List<StockDTO> stockList = list.stream()
										 .map(e->mapper.map(e, StockDTO.class))
										 .collect(Collectors.toList());
		return stockList;
	}

	
}
