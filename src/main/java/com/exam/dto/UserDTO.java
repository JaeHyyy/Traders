package com.exam.dto;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

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
	
	@NotEmpty(message = "아이디(이메일)입력은 필수입니다.")
	@Email(message = "유효한 이메일 주소를 입력해주세요.")
	String branchId; // 아이디(이메일형식)
	
	@NotEmpty(message = "비밀번호 입력은 필수입니다.")
	String passwd; // 비밀번호
	
	@NotEmpty(message = "사업자명 입력은 필수입니다.")
	String branchName; // 사업자명
	
	@NotEmpty(message = "사업자번호 입력은 필수입니다.")
	String branchNum; // 사업자번호
	
	@NotEmpty(message = "사업자등록증 등록은 필수입니다.")
	String branchImage; // 사업자등록증
	
	@NotEmpty(message = "우편번호 입력은 필수입니다.")
	String post; // 우편번호
	
	@NotEmpty(message = "지점주소 입력은 필수입니다.")
	String addr1; // 지점주소
	
	String addr2; // 지점주소2
	
	@NotEmpty(message = "전화번호 입력은 필수입니다.")
	@Pattern(regexp = "\\d{10,11}", message = "`-` 없이 입력해주세요.")
	String phoneNum; // 전화번호
	
//	String role;
}