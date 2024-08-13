package com.exam.dto;

import java.time.LocalDate;

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
public class PaymentDTO {
	
	String orderId;
	String orderName;
	String customerName;
	int amount;
}
