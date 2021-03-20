package com.example.demo.dto;

import java.util.LinkedList;

public class AccountStatement {

	private Account account;
	private LinkedList<AStatement> statements = new LinkedList<AStatement>();

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public LinkedList<AStatement> getStatements() {
		return statements;
	}

	public void addStatement(AStatement statement) {
		this.statements.add(statement);
	}

}
