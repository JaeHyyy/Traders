package com.exam.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer stockid;
	Integer inccode;
	Integer loccode;
	String gcode;
	Integer stockquantity;
	LocalDate expdate;
	Integer gprice;
}
