package com.rasime.bankingSystemwithMyBatish.entity;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("LogEntity")
public class LogEntity {
	private int id;
	private String operationType;
	private int accountId;
	private double balance;
	private int transferedAccount;
	private String type;
	
	
	


}
