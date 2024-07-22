package com.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.exam.entity.Goods;



public interface GoodsRepository extends JpaRepository<Goods, String> {

	/*
	 * 다음 메서드가 지원됨
	 * findAll()
	 * findById()
	 * count()
	 * delete()
	 * deleteById()
	 * 
	 * 이외의 변수로 조회하기 위해서는 추가로 메서드를 정의해야 된다.( Query Method 규칙을 따름 )
	 */
	
	List<Goods> findByGname(String gname);
	
}
