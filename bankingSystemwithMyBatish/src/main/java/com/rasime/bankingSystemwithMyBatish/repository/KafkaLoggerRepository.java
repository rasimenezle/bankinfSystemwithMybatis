package com.rasime.bankingSystemwithMyBatish.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.rasime.bankingSystemwithMyBatish.entity.LogEntity;

@Mapper
public interface KafkaLoggerRepository {

	public void saveLog(LogEntity log);

	@Select("Select * from kafka where id=#{id}")
	public LogEntity findLogDetailId(int id);

	@Select("Select * from kafka where id=#{id}")
	public List<LogEntity> findLogDetailByAccountId();
}
