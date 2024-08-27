package com.exam.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "ordercart")
public class OrderCart {
	
	//int에 null이 들어갈수없어서 타입들을 클래스형태로 바꿔줌
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int ordernum;
	
	String ordercode;
	
	Integer gcount;
	
	//연관 테이블 참조
	@ManyToOne  //다대일
	@JoinColumn(name = "gcode") //조인하는 컬럼
	Goods goods;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branchid", referencedColumnName = "branchId")
    private User user;

}
