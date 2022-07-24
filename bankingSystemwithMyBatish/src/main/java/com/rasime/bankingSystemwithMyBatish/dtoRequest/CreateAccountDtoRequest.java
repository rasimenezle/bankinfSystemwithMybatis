package com.rasime.bankingSystemwithMyBatish.dtoRequest;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountDtoRequest {

	private String name;
	private String surname;
	private String email;
	private String tc; 
	private String type;
	
	
}
