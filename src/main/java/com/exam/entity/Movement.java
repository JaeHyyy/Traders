package com.exam.entity;

import java.time.LocalDate;

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

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "movement")
public class Movement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long movidx;
	Long movcode;
	Long ordernum;
	String gcode;
	String branchid;
	LocalDate movdate;
	Long movquantity;
	String movstatus;
	String ordercode;
}
