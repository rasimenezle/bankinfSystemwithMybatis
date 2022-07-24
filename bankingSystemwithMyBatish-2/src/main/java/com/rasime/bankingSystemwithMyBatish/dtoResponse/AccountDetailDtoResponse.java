package com.rasime.bankingSystemwithMyBatish.dtoResponse;

import com.rasime.bankingSystemwithMyBatish.entity.AccountType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDetailDtoResponse {
	private String message;
	private String name;
	private String surname;
	private String email;
	private String tc;
	private AccountType type;
	private int number;
	private double balance;
	private boolean tinyint;
	
	private long lastModified;
}
