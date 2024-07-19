package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.EmpDTO;
import com.exam.service.EmpService;

@RestController
public class EmpController {
	
	EmpService empService;
	
	public EmpController(EmpService empService) {
		this.empService = empService;
	}


	@GetMapping("/findAll")
	public List<EmpDTO> findAll() {
		return empService.findAll();
	}
	
	@GetMapping("/findById/{empno}")
	public EmpDTO findById(@PathVariable long empno) {
		return empService.findById(empno);
	}
	
	@GetMapping("/findByEname/{ename}")
	public List<EmpDTO> findByEname(@PathVariable String ename) {
		return empService.findByEname(ename);
	}
	
	@PostMapping("/emp")
	public EmpDTO save(@RequestBody EmpDTO dto) {
		return empService.save(dto);
	}
	
	@DeleteMapping("/emp/{empno}")
	public void delete(@PathVariable long empno) {
		empService.delete(empno);
	}
	
	@PutMapping("/emp/{empno}")
	public void update(@PathVariable long empno,
			@RequestBody EmpDTO dto) {
		empService.update(empno, dto);
	}
	
}
