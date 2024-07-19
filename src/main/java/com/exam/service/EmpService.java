package com.exam.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.exam.dto.EmpDTO;


public interface EmpService {

	List<EmpDTO> findAll();
	EmpDTO findById(long empno);
	List<EmpDTO> findByEname(String ename);
	
	public EmpDTO save(EmpDTO dto);
	public void delete(long empno);
	public void update(long empno,
			EmpDTO dto);
	
}
