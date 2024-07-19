package com.exam.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Goods {
	
	//int에 null이 들어갈수없어서 타입들을 클래스형태로 바꿔줌
	@Id
	String gCode;
	String gCategory;
	String gName;
	Integer gCost_price;
	String gImage;
	String gCompany;
	String gUnit;
	

	

}
