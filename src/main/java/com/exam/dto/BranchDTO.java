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
	
	String branch_id;
	String passwd;
	String branch_name;
	String nickname;
	String branch_num;
	String branch_image;
	String post;
	String addr1;
	String addr2;
	String phone_num;

}