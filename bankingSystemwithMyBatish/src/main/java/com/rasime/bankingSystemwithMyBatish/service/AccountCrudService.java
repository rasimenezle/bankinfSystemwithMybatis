package com.rasime.bankingSystemwithMyBatish.service;

import com.rasime.bankingSystemwithMyBatish.dtoRequest.CreateAccountDtoRequest;
import com.rasime.bankingSystemwithMyBatish.dtoRequest.TransferAccountDtoRequest;
import com.rasime.bankingSystemwithMyBatish.dtoRequest.UpdateAccountDtoRequest;
import com.rasime.bankingSystemwithMyBatish.dtoResponse.GenericAccountDtoResponse;
import com.rasime.bankingSystemwithMyBatish.entity.Account;

public interface AccountCrudService {
	public int saveAccount(CreateAccountDtoRequest createAccountDtoRequest);

	public Account findById(int id);

	public void updateBalanceWithAccountId(int id, UpdateAccountDtoRequest updateAccountDtoRequest);

	public GenericAccountDtoResponse transferAmount(int id, TransferAccountDtoRequest transferAccountDtoRequest);

	public void deleteById(int id);
}
