package com.exam.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.config.SecurityConfig;
import com.exam.dto.UserDTO;
import com.exam.entity.User;
import com.exam.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, 
    		ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // 회원가입
    @Override
    public void save(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);
    }
    
    // 로그인
	@Override
	public User findByBranchId(String branchId) {
		// TODO Auto-generated method stub
		return userRepository.findByBranchId(branchId);
	}
    
    
    

    @Override
    public List<UserDTO> findAll() {
        List<User> branches = userRepository.findAll();
        return branches.stream()
                       .map(branch -> modelMapper.map(branch, UserDTO.class))
                       .collect(Collectors.toList());
    }




}
