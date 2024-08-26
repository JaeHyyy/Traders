package com.exam.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

    // 지점 정보 업데이트
    @Override
    public void updateBranchInfo(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userDTO.getBranchId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // 전달된 값만 업데이트
            if (userDTO.getPasswd() != null && !userDTO.getPasswd().isEmpty()) {
                user.setPasswd(userDTO.getPasswd());
            }
            if (userDTO.getBranchName() != null && !userDTO.getBranchName().isEmpty()) {
                user.setBranchName(userDTO.getBranchName());
            }
            if (userDTO.getBranchNum() != null && !userDTO.getBranchNum().isEmpty()) {
                user.setBranchNum(userDTO.getBranchNum());
            }
            if (userDTO.getPost() != null && !userDTO.getPost().isEmpty()) {
                user.setPost(userDTO.getPost());
            }
            if (userDTO.getAddr1() != null && !userDTO.getAddr1().isEmpty()) {
                user.setAddr1(userDTO.getAddr1());
            }
            if (userDTO.getAddr2() != null && !userDTO.getAddr2().isEmpty()) {
                user.setAddr2(userDTO.getAddr2());
            }
            if (userDTO.getPhoneNum() != null && !userDTO.getPhoneNum().isEmpty()) {
                user.setPhoneNum(userDTO.getPhoneNum());
            }

            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
    
    @Override
    public UserDTO getUserByBranchId(String branchId) {
        User user = userRepository.findByBranchId(branchId);
        if (user != null) {
            return modelMapper.map(user, UserDTO.class);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
    
    @Override
    public boolean validatePassword(String branchId, String rawPassword) {
        User user = userRepository.findByBranchId(branchId);
        return user != null && passwordEncoder.matches(rawPassword, user.getPasswd());
    }

    @Override
    public String changePassword(String branchId, String newPassword) {
        User user = userRepository.findByBranchId(branchId);
        if (user == null) {
            return "User not found";
        }

        user.setPasswd(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "SUCCESS";
    }

}
