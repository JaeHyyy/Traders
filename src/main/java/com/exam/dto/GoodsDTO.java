package com.exam.dto;

import org.springframework.web.multipart.MultipartFile;

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
public class GoodsDTO {
	
	
	String gcode;
	String gcategory;
	String gname;
	Integer gcostprice;
	String gimage;
	String gcompany;
	String gunit;
	
    MultipartFile multipartFile;
}
