package com.sv.repositories;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.sv.framework.ApplicationConstants;
import com.sv.framework.TrackerDaoFactory;
import com.sv.framework.exceptions.TrackerException;
import com.sv.vo.SearchConfig;

@Repository
public class SearchDao extends TrackerDaoFactory {
	
private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<SearchConfig> getSearchCriterias(String searchOn) throws TrackerException {

		logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		
		List<SearchConfig> searchConfigList = new ArrayList<>(1);
		try {
			String sql = "select * from search_config where search_on = :searchOn";
			
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("searchOn", searchOn);
	        	        			
			searchConfigList = getNamedParameterJdbcTemplate().query(sql, param, new BeanPropertyRowMapper<>(SearchConfig.class));
			
		} catch (DataAccessException e) {
			logger.error("DataAccessException:", e);
			throw new TrackerException("1000", "Exception while fetching SearchCriteria");
		} finally {
			logger.info(ApplicationConstants.LOG_EXIT_MESSAGE);
		}
		
		return searchConfigList;		
	
	}


}
