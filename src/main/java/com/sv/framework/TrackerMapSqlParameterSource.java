package com.sv.framework;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.sv.vo.SearchCriteria;

public class TrackerMapSqlParameterSource {

	public static SqlParameterSource mapSearchCriteria(List<SearchCriteria> searchCriteriaList) {
		
		MapSqlParameterSource param = new MapSqlParameterSource();
		
		
		for(SearchCriteria criteria : searchCriteriaList)
		{
			if(!"".equals(checkNull(criteria.getFieldValue())))
			{
				if("string".equals(criteria.getFieldType()))
				{
					if("in".equals(criteria.getFieldFilter()))
					{
						param.addValue(getMapper(criteria.getFieldName()),getSet(criteria.getFieldValue()));
					}else if("like".equals(criteria.getFieldFilter())){
						param.addValue(getMapper(criteria.getFieldName()), "%"+criteria.getFieldValue()+"%");
					}else{
						param.addValue(getMapper(criteria.getFieldName()),new String(criteria.getFieldValue()));
					}
					
				}else if ("number".equals(criteria.getFieldType()))
				{
					param.addValue(getMapper(criteria.getFieldName()),new Integer(criteria.getFieldValue()));
				}else if ("date".equals(criteria.getFieldType()))
				{
					param.addValue(getMapper(criteria.getFieldName()),new Date(criteria.getFieldValue()));
				}else{
					param.addValue(getMapper(criteria.getFieldName()),new String(criteria.getFieldValue()));
				}
			}			
		}
		
		return param;		
		
	}
	
	private static Set<String> getSet(String fieldValue) {
		
		Set<String> retSet = new HashSet<String>();
		
		if(fieldValue != null)
		{
			String[] values = fieldValue.split(",");
			retSet = new HashSet<String>(Arrays.asList(values));
		}
		return retSet;
		
	}

	private static String getMapper(String fieldName) {
		
		String mapperName = "";
		if(fieldName != null)
		{
			mapperName = fieldName.trim().toLowerCase();
			mapperName = mapperName.replace(" ", "_");
		}
		return mapperName;
	}
	
	private static String checkNull(String value) {
		return value == null ? "": value;
	}

	
	
	
	
	
}
