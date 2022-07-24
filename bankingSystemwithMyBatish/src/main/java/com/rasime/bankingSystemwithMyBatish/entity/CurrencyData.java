package com.rasime.bankingSystemwithMyBatish.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyData {

	@JsonProperty
	public String code;
	@JsonProperty
	public String name;
	@JsonProperty
	public String rate;
	@JsonProperty
	public String calculatedstr;
	@JsonProperty
	public double calculated;

}
