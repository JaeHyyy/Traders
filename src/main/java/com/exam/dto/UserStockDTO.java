package com.exam.dto;

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
public class UserStockDTO {
	private UserDTO user;
	private StockDTO stock;
	private GoodsDTO goods;
	
	String branchId;
	String branchName;
	long count;
}
