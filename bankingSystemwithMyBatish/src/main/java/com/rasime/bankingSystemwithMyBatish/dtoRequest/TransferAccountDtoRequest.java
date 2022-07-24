package com.rasime.bankingSystemwithMyBatish.dtoRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferAccountDtoRequest {
	private String id;
	private double balance;
	
	
	
	

}
