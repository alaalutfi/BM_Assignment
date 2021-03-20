package com.example.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.AStatement;
import com.example.demo.dto.Account;
import com.example.demo.dto.AccountStatement;
import com.example.demo.exception.RecordNotFoundException;

@Repository
public class AccountStatemntRepoImpl implements AccountStatemntRepo {

	@Autowired
	private JdbcTemplate template;

	public AccountStatement queryStatements(int id) {
	

		AccountStatement accStatement;
		try {
			accStatement = template.query(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					// TODO Auto-generated method stub
					String sql = "SELECT A.id,A.account_type,A.account_number,S.id,S.datefield,S.amount FROM ACCOUNT A INNER JOIN STATEMENT S ON A.ID = S.account_ID WHERE A.ID=?";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setInt(1, id);
					
					return ps;
				}
			},  new ResultSetExtractor<AccountStatement>() {

				@Override
				public AccountStatement extractData(ResultSet rs) throws SQLException, DataAccessException {
					// TODO Auto-generated method stubAccount account = new Account();

					rs.next();

					AccountStatement accStatement = new AccountStatement();

					Account account = new Account();
					account.setId(rs.getInt(1));
					account.setAccount_type(rs.getString(2));
					account.setAccount_number(rs.getString(3));

					accStatement.setAccount(account);
					do {
						AStatement statement = new AStatement();

						statement.setAccount_id(account.getId());
						statement.setId(rs.getInt(4));
						statement.setDateField(rs.getString(5));
						statement.setAmount(rs.getString(6));

						accStatement.addStatement(statement);
					} while (rs.next());

					return accStatement;
				}

			});
		} 
		catch (UncategorizedSQLException ex) {
			// TODO: handle exception
			throw new RecordNotFoundException("account id 	"+id+" not found");
		}
		
		return accStatement;
		

	}

	
	
}
