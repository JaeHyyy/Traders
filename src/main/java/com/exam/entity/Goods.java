package com.exam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "goods")
public class Goods {
	
	//int에 null이 들어갈수없어서 타입들을 클래스형태로 바꿔줌
	@Id
	String gcode;
	String gcategory;
	String gname;
	@Column(nullable = true)
	Integer gcostprice;
	String gimage;
	String gcompany;
	String gunit;
}
