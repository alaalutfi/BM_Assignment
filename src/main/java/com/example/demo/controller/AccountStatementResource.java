package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AccountStatement;
import com.example.demo.service.AccountStatementService;

@RestController
public class AccountStatementResource {

	@Autowired
	private AccountStatementService accountStatementService;
	
	@GetMapping("/accountStatement/{id}")
	public AccountStatement viewStatements(SecurityContextHolderAwareRequestWrapper requestWrapper,
			@PathVariable int id,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate fromDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate toDate,
			@RequestParam(required = false) Double fromAmnt,
			@RequestParam(required = false) Double toAmnt) {
		return accountStatementService.getStatements(requestWrapper,id,fromDate,toDate,fromAmnt,toAmnt);
	}
}
