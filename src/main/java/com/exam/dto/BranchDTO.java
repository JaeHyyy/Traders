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
public class BranchDTO {
	
	String branchId;
	
	String passwd;
	String branchName;
	String nickname;
	String branchNum;
	String branchImage;
	String post;
	String addr1;
	String addr2;
	String phoneNum;

}