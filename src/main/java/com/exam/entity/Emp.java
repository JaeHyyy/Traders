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
public class Emp {
	
	//int에 null이 들어갈수없어서 타입들을 클래스형태로 바꿔줌
	@Id
	Long empno;
	String ename;
	String job;
	Long mgr;
	LocalDate hiredate;
	Integer sal;
	@Column(nullable = true)
	Integer comm;
	
	//연관 테이블 참조
	@ManyToOne   //다대일
	@JoinColumn(name = "deptno") //조인하는 컬럼 쓰면됌
	Branch dept;
	

}
