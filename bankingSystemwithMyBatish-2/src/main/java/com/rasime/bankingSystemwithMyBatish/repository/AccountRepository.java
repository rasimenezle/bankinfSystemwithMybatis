package com.rasime.bankingSystemwithMyBatish.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import com.rasime.bankingSystemwithMyBatish.entity.Account;

@Mapper
public interface AccountRepository {

	// xml ile denedim diğerleri annotation
	@Transactional
	public void insertWithAccountNumber(Account createAccountDtoRequest);

	@Transactional
	@Select("select * from account where id = #{id}")
	public Account findByAccountId(int id);

	@Transactional
	@Update("update account set balance=#{balance},lastModified=#{lastModified} where id=#{id}")
	public void updateBalanceWithAccountId(int id, double balance, long lastModified);

	@Transactional
	@Update("update account set deleteAccount=#{deleteAccount},lastModified=#{lastModified} where id=#{id}")
	public void deleteAccountById(int id, boolean deleteAccount, long lastModified);

}
