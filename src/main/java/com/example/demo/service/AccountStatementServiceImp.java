package com.example.demo.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AStatement;
import com.example.demo.dto.AccountStatement;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.repository.AccountStatemntRepo;

@Service
public class AccountStatementServiceImp implements AccountStatementService{

	@Autowired
	private AccountStatemntRepo accountStatemnetRepo;
	
	public AccountStatement getStatements(SecurityContextHolderAwareRequestWrapper requestWrapper,int accountID,LocalDate fromDate,LocalDate toDate,Double fromAmnt,Double toAmnt)  {
		 AccountStatement queryStatements = accountStatemnetRepo.queryStatements(accountID);
		 
		 boolean adminUser = requestWrapper.isUserInRole("ADMIN");
		
		 
		//check if no parameter exist , default behaviour for last 3 months shall be
		 if(fromDate == null && toDate == null && fromAmnt == null && toAmnt == null) {
			 LocalDate lastThreeMonths = LocalDate.now().minusMonths(3);
			 applyFromDateFilters(queryStatements, lastThreeMonths);
		 }else {
			 if(!adminUser) {
				 throw new UnauthorizedException("user doesn't have admin role");
			 }
		 }
		 
		
		 //hash the account number
		 String account_number = queryStatements.getAccount().getAccount_number();
		 String hashedAccountNumber = encodesha256(account_number);
		 queryStatements.getAccount().setAccount_number(hashedAccountNumber);
		 
		 
		 
		 if(fromDate != null) {
			 applyFromDateFilters(queryStatements, fromDate);
		 }
		 if(toDate != null) {
			 applyToDateFilters(queryStatements, toDate);
		 }
		 if(fromAmnt != null) {
			 applyFromAmountFilters(queryStatements, fromAmnt);
		 }
		 if(toAmnt != null) {
			 applyToAmountFilters(queryStatements, toAmnt);
		 }
		 
		 return queryStatements;
	}
	
	private void applyFromDateFilters(AccountStatement accStatements,LocalDate from) {
		LinkedList<AStatement> statements = accStatements.getStatements();
		Iterator<AStatement> iterator = statements.iterator();
		while(iterator.hasNext()) {
			AStatement next = iterator.next();
			LocalDate converted = convert(next.getDateField());
			if(!(converted.compareTo(from) >= 0)){
				iterator.remove();
			}
		}
	}
	
	private void applyToDateFilters(AccountStatement accStatements,LocalDate to) {
		LinkedList<AStatement> statements = accStatements.getStatements();
		Iterator<AStatement> iterator = statements.iterator();
		while(iterator.hasNext()) {
			AStatement next = iterator.next();
			LocalDate converted = convert(next.getDateField());
			if(!(converted.compareTo(to) <= 0)){
				iterator.remove();
			}
		}
	}
	
	private void applyFromAmountFilters(AccountStatement accStatements,Double from) {
		LinkedList<AStatement> statements = accStatements.getStatements();
		Iterator<AStatement> iterator = statements.iterator();
		while(iterator.hasNext()) {
			AStatement next = iterator.next();
			double amount = Double.parseDouble(next.getAmount());
			if(!(amount >= from)){
				iterator.remove();
			}
		}
	}
	
	private void applyToAmountFilters(AccountStatement accStatements,Double to) {
		LinkedList<AStatement> statements = accStatements.getStatements();
		Iterator<AStatement> iterator = statements.iterator();
		while(iterator.hasNext()) {
			AStatement next = iterator.next();
			double amount = Double.parseDouble(next.getAmount());
			if(!(amount <= to)){
				iterator.remove();
			}
		}
	}
	
/*	private void applyDateFilters(AccountStatement accStatements,LocalDate from,LocalDate to) {
		LinkedList<AStatement> statements = accStatements.getStatements();
		Iterator<AStatement> iterator = statements.iterator();
		while(iterator.hasNext()) {
			AStatement next = iterator.next();
			LocalDate converted = convert(next.getDateField());
			if(!(converted.compareTo(from) >= 0 && converted.compareTo(to) <= 0)){
				iterator.remove();
			}
		}
	}
	
	private void applyAmountFilters(AccountStatement accStatements,Double from,Double to) {
		LinkedList<AStatement> statements = accStatements.getStatements();
		Iterator<AStatement> iterator = statements.iterator();
		while(iterator.hasNext()) {
			AStatement next = iterator.next();
			double amount = Double.parseDouble(next.getAmount());
			if(!(amount >= from && amount <= to)){
				iterator.remove();
			}
		}
	}*/
	
	private LocalDate convert(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
	
	public String encodesha256(String originalString) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA3-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final byte[] hashbytes = digest.digest(
		  originalString.getBytes(StandardCharsets.UTF_8));
		String sha3Hex = bytesToHex(hashbytes);
		return sha3Hex;
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
}
