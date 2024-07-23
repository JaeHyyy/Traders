package com.exam.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

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
public class Stock {
	
	@Id
	Integer stockid;
	Integer stockquantity;
	LocalDate expdate;
	Integer gprice;
	String loc1;
	String loc2;
	String loc3;
	
	@OneToMany
	@JoinColumn(name = "gcode")
	Goods goods;
}
