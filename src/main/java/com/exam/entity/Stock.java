package com.exam.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "stock")
public class Stock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int stockid;
	Integer stockquantity;
	LocalDate expdate;
	Integer gprice;
	String loc1;
	String loc2;
	String loc3;
	
	@Column(name = "gcode")
	String gcode;
	
	@ManyToOne
	@JoinColumn(name = "gcode", referencedColumnName = "gcode", insertable = false, updatable = false)
	Goods goods;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branchid", referencedColumnName = "branchId")
    private User user;
}