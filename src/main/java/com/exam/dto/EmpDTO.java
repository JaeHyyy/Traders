package com.exam.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.exam.entity.Branch;

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
public class EmpDTO {
	
	Long empno;
	String ename;
	String job;
	Long mgr;
	LocalDate hiredate;
	Integer sal;
	Integer comm;
	
	//연관 테이블 참조
	@ManyToOne   //다대일
	@JoinColumn(name = "deptno") //조인하는 컬럼 쓰면됌
	BranchDTO dept;
	

}
