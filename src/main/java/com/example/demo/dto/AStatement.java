package com.example.demo.dto;

public class AStatement {

	private int id;
	private int account_id;
	private String dateField;
	private String amount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public String getDateField() {
		return dateField;
	}

	public void setDateField(String dateField) {
		this.dateField = dateField;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
