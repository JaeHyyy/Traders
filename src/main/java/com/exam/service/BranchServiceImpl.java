package com.exam.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.config.SecurityConfig;
import com.exam.dto.BranchDTO;
import com.exam.entity.Branch;
import com.exam.repository.BranchRepository;

@Service
@Transactional
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;

    public BranchServiceImpl(BranchRepository branchRepository, 
    		ModelMapper modelMapper) {
        this.branchRepository = branchRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(BranchDTO branchDTO) {
        Branch branch = modelMapper.map(branchDTO, Branch.class);
        branchRepository.save(branch);
    }

    @Override
    public List<BranchDTO> findAll() {
        List<Branch> branches = branchRepository.findAll();
        return branches.stream()
                       .map(branch -> modelMapper.map(branch, BranchDTO.class))
                       .collect(Collectors.toList());
    }
}
