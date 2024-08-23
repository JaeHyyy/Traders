package com.exam.service;

import java.util.List;

import com.exam.dto.UserDTO;
import com.exam.entity.User;

public interface UserService {
	
	void save(UserDTO userDTO); // 회원가입
	User findByBranchId(String branchId); // 로그인
	List<UserDTO> findAll();
	
}
