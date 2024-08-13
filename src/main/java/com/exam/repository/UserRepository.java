package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.dto.UserDTO;
import com.exam.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	int save(UserDTO userDTO); // 회원가입
	User findByBranchId(String branchId); // 로그인
	
}
