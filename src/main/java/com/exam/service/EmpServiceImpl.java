package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.EmpDTO;
import com.exam.entity.Emp;
import com.exam.repository.EmpRepository;

@Service
@Transactional
public class EmpServiceImpl implements EmpService {
	
	EmpRepository empRepository;
	
	public EmpServiceImpl(EmpRepository empRepository) {
		this.empRepository = empRepository;
	}


	@Override
	public List<EmpDTO> findAll() {
		
		ModelMapper mapper = new ModelMapper();
		
		List <Emp> list = empRepository.findAll();
		
		List<EmpDTO> empList = list.stream()
				.map(e->mapper.map(e, EmpDTO.class))
				.collect(Collectors.toList());
		return empList;
	}


	@Override
	public EmpDTO findById(long empno) {
		
		ModelMapper mapper = new ModelMapper();
		Emp emp = empRepository.findById(empno).orElse(null);
		
		EmpDTO dto = mapper.map(emp, EmpDTO.class);
		                    //두번째는 변경할 DTO를 클래스로 넣으면됌
		System.out.println("Emp:" + emp.getEname());
		System.out.println("EmpDTO:" + dto.getEname());
		
//		EmpDTO dto = EmpDTO.builder()
//				.empno(emp.getEmpno())
//				.ename(emp.getEname())
//				.job(emp.getJob())
//				.mgr(emp.getMgr())
//				.hiredate(emp.getHiredate())
//				.sal(emp.getSal())
//				.comm(emp.getComm())
////				.dept(emp.getDept())
//				.build();
//		dto.setDept(emp.getDept());
		return null;
	}


	@Override
	public List<EmpDTO> findByEname(String ename) {
		ModelMapper mapper = new ModelMapper();
		List <Emp> list = empRepository.findByEname(ename);
		
		List<EmpDTO> empList = list.stream()
				.map(e->mapper.map(e, EmpDTO.class))
				.collect(Collectors.toList());
		return empList;
	}


	@Override
	public EmpDTO save(EmpDTO dto) {
		ModelMapper mapper = new ModelMapper();
		Emp emp = mapper.map(dto, Emp.class);
		empRepository.save(emp);
		return null;
	}


	@Override
	public void delete(long empno) {
		//삭제할걸 찾아야함 findById                 옵셔널
		Emp emp = empRepository.findById(empno).orElse(null);
		if(emp!=null) {
			empRepository.delete(emp);			
		}
		
	}


	@Override
	public void update(long empno, EmpDTO dto) {
		Emp emp = empRepository.findById(empno).orElse(null);
		
		//더티체킹
		emp.setEname(dto.getEname());
		emp.setSal(dto.getSal());
	}

}
