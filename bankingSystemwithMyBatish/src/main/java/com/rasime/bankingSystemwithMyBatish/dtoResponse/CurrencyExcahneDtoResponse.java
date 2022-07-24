package com.rasime.bankingSystemwithMyBatish.dtoResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyExcahneDtoResponse {
	
	private String code;
	private String name;
	private String rate;
	private String calculatedstr;
	private double calculated;
	

}
