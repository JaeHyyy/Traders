package com.exam.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.BranchDTO;
import com.exam.entity.Branch;
import com.exam.repository.BranchRepository;

@Service
@Transactional
public class BranchServiceImpl implements BranchService{

	BranchRepository branchRepository;
	ModelMapper modelMapper;

	public BranchServiceImpl(BranchRepository branchRepository) {
		this.branchRepository = branchRepository;
	}
	
    @Override
    public int save(BranchDTO branchDTO) {
        Branch branch = modelMapper.map(branchDTO, Branch.class);
        branchRepository.save(branch);
        return 1;
    }
	
	@Override
	public List<BranchDTO> findAll(){
		ModelMapper mapper = new ModelMapper();
		
		List<Branch> list = branchRepository.findAll();
		List<BranchDTO> branchList = list.stream()
										 .map(e->mapper.map(e, BranchDTO.class))
										 .collect(Collectors.toList());
		return branchList;
	}





}