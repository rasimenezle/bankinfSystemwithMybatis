package com.rasime.bankingSystemwithMyBatish.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rasime.bankingSystemwithMyBatish.entity.LogEntity;
import com.rasime.bankingSystemwithMyBatish.repository.KafkaLoggerRepository;

@Component
public class Consumer {

	@Autowired
	KafkaLoggerRepository repository;

	@KafkaListener(topics = "json", groupId = "json")

// Method
	public void consume(@Payload String log) {
		List<String> myList = new ArrayList<String>(Arrays.asList(log.split(" ")));
		LogEntity logs = new LogEntity();
		if (myList.size() == 4) {
			logs.setAccountId(Integer.parseInt(myList.get(0)));
			logs.setOperationType(myList.get(1));
			logs.setBalance(Double.parseDouble(myList.get(2)));
			logs.setType(myList.get(3));
		} else {
			logs.setAccountId(Integer.parseInt(myList.get(0)));
			logs.setOperationType(myList.get(1));
			logs.setBalance(Double.parseDouble(myList.get(2)));
			logs.setType(myList.get(3));
			logs.setTransferedAccount(Integer.parseInt(myList.get(4)));
		}

		repository.saveLog(logs);
		

	}

}