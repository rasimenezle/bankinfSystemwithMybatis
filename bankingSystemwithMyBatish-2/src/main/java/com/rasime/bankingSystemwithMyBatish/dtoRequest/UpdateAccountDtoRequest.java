package com.rasime.bankingSystemwithMyBatish.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAccountDtoRequest {
	private double balance;
	private String operationType;
}
