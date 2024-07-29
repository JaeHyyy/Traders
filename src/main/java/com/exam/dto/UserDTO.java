package com.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserDTO {
	
	String branchId; // 아이디(이메일형식)
	
	String passwd; // 비밀번호
	String branchName; // 사업자명
	String nickname; // 닉네임
	String branchNum; // 사업자번호
	String branchImage; // 사업자등록증
	String post; // 우편번호
	String addr1; // 지점주소
	String addr2; // 지점주소2
	String phoneNum; // 전화번호
}