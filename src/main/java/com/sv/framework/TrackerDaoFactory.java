package com.sv.framework;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;


public class TrackerDaoFactory {

	@Autowired
	private DataSource dataSource;
		
	public JdbcTemplate getJdbcTemplate() {
		
		JdbcTemplate template = new JdbcTemplate(dataSource);
		
		return template;
	}//end of getJdbctemplate().
	
	
	protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		
		
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		
		return template;
	}//end of getNamedParameterJdbcTemplate().
	
	
	
	protected SimpleJdbcInsert getSimpleJdbcInsert() {
		
		SimpleJdbcInsert template = new SimpleJdbcInsert(dataSource);
		
		return template;

	}//end of getSimpleJdbcInsert().
	
	protected SimpleJdbcCall  getSimpleJdbcCall() {
		
		SimpleJdbcCall template = new  SimpleJdbcCall(dataSource);
		
		return template;
	}//end of getSimpleJdbcCall().
		
}
