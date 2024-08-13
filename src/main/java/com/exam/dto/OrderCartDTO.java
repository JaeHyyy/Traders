package com.exam.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Id;

import com.exam.entity.Goods;

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
public class OrderCartDTO {
	
	
	String ordercode;
	Integer gcount;
	GoodsDTO goods;
	

}
