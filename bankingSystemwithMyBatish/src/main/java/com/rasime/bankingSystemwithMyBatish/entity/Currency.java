package com.rasime.bankingSystemwithMyBatish.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currency {
	private boolean success;
	private CurrencyResult result;

}
