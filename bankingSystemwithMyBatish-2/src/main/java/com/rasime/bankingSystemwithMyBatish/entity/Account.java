package com.rasime.bankingSystemwithMyBatish.entity;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("Account")
public class Account {

	private int id;
	private String name;
	private String surname;
	private String email;
	private String tc;
	private AccountType type;
	private double balance;
	private boolean deleteAccount;
	private long lastModified;

}
