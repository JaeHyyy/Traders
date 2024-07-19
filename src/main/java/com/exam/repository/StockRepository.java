package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer>{
	
}
