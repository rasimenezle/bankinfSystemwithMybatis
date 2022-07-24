package com.rasime.bankingSystemwithMyBatish.currency;

public interface CurrencyExchangeUnit {
	public double currencyChange(double balance, String accountType, String accountType2);

	public double buyingGramOns(double balance, String accountType, String accountType2);

	public double sellingGramOns(double balance, String accountType, String accountType2);

}
