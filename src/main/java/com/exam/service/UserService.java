package com.exam.service;

import java.util.List;

import com.exam.dto.UserDTO;
import com.exam.entity.User;

public interface UserService {
	
	void save(UserDTO userDTO); // 회원가입
	User findByBranchId(String branchId); // 로그인
	List<UserDTO> findAll();
	void updateBranchInfo(UserDTO userDTO); // 지점 정보 업데이트
	UserDTO getUserByBranchId(String branchId); //지점 정보 조회 - 마이페이지에 기존의 정보를 보여주는 용도
	boolean validatePassword(String branchId, String rawPassword); //현재 비밀번호와 비교 및 검증
    String changePassword(String branchId, String newPassword); // 비밀번호 변경
}
