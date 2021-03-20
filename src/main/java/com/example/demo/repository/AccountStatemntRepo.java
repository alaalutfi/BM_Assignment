package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.AccountStatement;

@Repository
public interface AccountStatemntRepo {

	public AccountStatement queryStatements(int accountID);
}
