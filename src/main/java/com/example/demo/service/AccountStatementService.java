package com.example.demo.service;

import java.time.LocalDate;

import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AccountStatement;

@Service
public interface AccountStatementService {

	public AccountStatement getStatements(SecurityContextHolderAwareRequestWrapper requestWrapper,
			int accountID,LocalDate fromDate,LocalDate toDate,Double fromAmnt,Double toAmnt) ;
}
