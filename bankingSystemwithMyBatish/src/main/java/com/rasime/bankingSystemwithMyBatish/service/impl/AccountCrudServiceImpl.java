package com.rasime.bankingSystemwithMyBatish.service.impl;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.rasime.bankingSystemwithMyBatish.currency.CurrencyExchangeUnit;
import com.rasime.bankingSystemwithMyBatish.dtoRequest.CreateAccountDtoRequest;
import com.rasime.bankingSystemwithMyBatish.dtoRequest.TransferAccountDtoRequest;
import com.rasime.bankingSystemwithMyBatish.dtoRequest.UpdateAccountDtoRequest;
import com.rasime.bankingSystemwithMyBatish.dtoResponse.GenericAccountDtoResponse;
import com.rasime.bankingSystemwithMyBatish.entity.Account;
import com.rasime.bankingSystemwithMyBatish.entity.AccountType;
import com.rasime.bankingSystemwithMyBatish.entity.LogEntity;
import com.rasime.bankingSystemwithMyBatish.repository.AccountRepository;
import com.rasime.bankingSystemwithMyBatish.service.AccountCrudService;

@Service

public class AccountCrudServiceImpl implements AccountCrudService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CurrencyExchangeUnit exchange;

	@Autowired
	KafkaTemplate<String, String> produce;
	Date date = new Date();
	LogEntity log = new LogEntity();

//	Create account ServiceImpl Detail

	@Override
	public int saveAccount(CreateAccountDtoRequest createAccountDtoRequest) {
		Account accountRequest = modelMapper.map(createAccountDtoRequest, Account.class);

		accountRequest.setBalance(0);
		Date date = new Date();
		accountRequest.setLastModified(date.getTime());

		accountRepository.insertWithAccountNumber(accountRequest);
		return accountRequest.getId();

	}

//	Update account ServiceImpl Detail
	@Override
	public void updateBalanceWithAccountId(int id, UpdateAccountDtoRequest updateAccountDtoRequest) {
		Account account = accountRepository.findByAccountId(id);

		account.setBalance(account.getBalance() + updateAccountDtoRequest.getBalance());
		account.setLastModified(date.getTime());
		this.accountRepository.updateBalanceWithAccountId(account.getId(), account.getBalance(),
				account.getLastModified());

		// producer send message as String
		log.setAccountId(account.getId());
		log.setBalance(updateAccountDtoRequest.getBalance());
		log.setOperationType("deposit");
		String logs = log.getAccountId() + " " + log.getOperationType() + " " + log.getBalance() + " " + log.getType();
		produce.send("json", logs);

	}

//Transfer Amount ServiceImpl Detail
	@Override
	public GenericAccountDtoResponse transferAmount(int id, TransferAccountDtoRequest transferAccountDtoRequest) {

		Account accountTransfer = accountRepository.findByAccountId(id);
		int transferId = Integer.parseInt(transferAccountDtoRequest.getId());
		Account transferedAccount = accountRepository.findByAccountId(transferId);

		String result = "Insufficient balance";
		double amount = 0;
		double altınDolar = 55;

		// If the amount to be sent is greater than the sender's balance, it will be an
		// invalid request.
		if ((accountTransfer.getBalance() > 0)
				&& (transferAccountDtoRequest.getBalance() <= accountTransfer.getBalance())) {

			accountTransfer.setBalance(accountTransfer.getBalance() - transferAccountDtoRequest.getBalance());
			accountTransfer.setLastModified(date.getTime());
			result = "Transferred Successfully";
			accountRepository.updateBalanceWithAccountId(accountTransfer.getId(), accountTransfer.getBalance(),
					accountTransfer.getLastModified());
		}

		if (accountTransfer.getType().equals(transferedAccount.getType())) {

			amount = transferAccountDtoRequest.getBalance();

		} else if (accountTransfer.getType().equals(AccountType.TRY)
				&& transferedAccount.getType().equals(AccountType.USD)) {

			amount = (transferAccountDtoRequest.getBalance()) / exchange.currencyChange(transferedAccount.getBalance(),
					accountTransfer.getType().toString(), transferedAccount.getType().toString());

		} else if (accountTransfer.getType().equals(AccountType.TRY)
				&& transferedAccount.getType().equals(AccountType.ONS)) {
			amount = (transferAccountDtoRequest.getBalance()) / exchange.buyingGramOns(transferedAccount.getBalance(),
					accountTransfer.getType().toString(), transferedAccount.getType().toString());
		} else if (accountTransfer.getType().equals(AccountType.USD)
				&& transferedAccount.getType().equals(AccountType.TRY)) {
			amount = (transferAccountDtoRequest.getBalance()) * exchange.currencyChange(transferedAccount.getBalance(),
					accountTransfer.getType().toString(), transferedAccount.getType().toString());
		} else if (accountTransfer.getType().equals(AccountType.ONS)
				&& transferedAccount.getType().equals(AccountType.TRY)) {
			amount = (transferAccountDtoRequest.getBalance()) * exchange.sellingGramOns(transferedAccount.getBalance(),
					accountTransfer.getType().toString(), transferedAccount.getType().toString());
		}

		else if (accountTransfer.getType().equals(AccountType.ONS)
				&& transferedAccount.getType().equals(AccountType.USD)) {
			amount = (transferAccountDtoRequest.getBalance())
					* (exchange.sellingGramOns(transferedAccount.getBalance(), accountTransfer.getType().toString(),
							transferedAccount.getType().toString()))
					/ exchange.currencyChange(transferedAccount.getBalance(), accountTransfer.getType().toString(),
							transferedAccount.getType().toString());

		}

		else {
			amount = (transferAccountDtoRequest.getBalance()) / altınDolar;

		}

		transferedAccount.setBalance(transferedAccount.getBalance() + amount);
		transferedAccount.setLastModified(date.getTime());
		accountRepository.updateBalanceWithAccountId(transferedAccount.getId(), transferedAccount.getBalance(),
				transferedAccount.getLastModified());

		log.setAccountId(accountTransfer.getId());
		log.setBalance(transferAccountDtoRequest.getBalance());
		log.setOperationType("transfer");
		log.setTransferedAccount(transferId);
		log.setType(transferedAccount.getType().toString());
		String logs = log.getAccountId() + " " + log.getOperationType() + " " + log.getBalance() + " " + log.getType()
				+ " " + log.getTransferedAccount();
		produce.send("json", logs);
		return GenericAccountDtoResponse.builder().message(result).build();
	}

	@Override
	public Account findById(int id) {
		Account account = accountRepository.findByAccountId(id);
		return account;
	}

	@Override
	public void deleteById(int id) {
		Account account = accountRepository.findByAccountId(id);
		account.setLastModified(date.getTime());
		accountRepository.deleteAccountById(id, true, account.getLastModified());

	}

}
