package com.sv.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sv.framework.exceptions.TrackerException;
import com.sv.vo.SearchCriteria;

public class DynamicQueryGenerator {

	public static String generateSearch(String sql, Map<String, String> joiner,
			List<SearchCriteria> searchCriteriaList) throws TrackerException {
		
		String generatedQry = sql;
		if(searchCriteriaList != null)
		{
			int validFilter = 0;
			
			cleanUpSearchCriteria(searchCriteriaList);
			
			int nonHiddenSearchListCount = getNonHiddenSearchCount(searchCriteriaList);
			for(SearchCriteria criteria : searchCriteriaList)
			{

				validFilter++;
				
				if(!joiner.containsKey(criteria.getFieldName()))
				{
					throw new TrackerException("1000", criteria.getFieldName() + " not found in joiner");
				}
				
				if(criteria.getFieldFilter() == null)
				{
					throw new TrackerException("1000", criteria.getFieldName() + " missed search filter");
				}					
				
				if(validFilter > 1 && validFilter != nonHiddenSearchListCount && criteria.getLogicalCdn() == null)
				{
					throw new TrackerException("1000", criteria.getFieldName() + " missed join condition");
				}
				
				if(validFilter == 1)
				{
					if(generatedQry.toLowerCase().contains("where"))
					{
						generatedQry += " and ";
					}else{
						generatedQry += " where ";	
					}
					 
				}			
				
				generatedQry += " " + joiner.get(criteria.getFieldName());
				generatedQry += " " + criteria.getFieldFilter();
				
				String filter = criteria.getFieldFilter();
				if("in".equalsIgnoreCase(filter))
				{
					generatedQry += " (";
					generatedQry += getMapper(criteria.getFieldName());
					generatedQry += " )";
				}else{
					generatedQry += " " + getMapper(criteria.getFieldName());
				}
				
				// dynamically adding Hidden Criteria not appended earlier search criteria logical cdns
				if(validFilter == 1 && searchCriteriaList.size() > 1 && 
						("".equals(checkNull(criteria.getLogicalCdn()))))
				{
					generatedQry += " AND ";
				}else 
				{
					if(validFilter != searchCriteriaList.size())
					{
						if("".equals(checkNull(criteria.getLogicalCdn())))
						{
							generatedQry += " AND ";
						}else{
							generatedQry += " " + criteria.getLogicalCdn();	
						}
												
					}					
				}								
			}
		}
		
		return generatedQry;
		
		
	}

	private static int getNonHiddenSearchCount(List<SearchCriteria> searchCriteriaList) {
		
		int count = searchCriteriaList.size();
		for(SearchCriteria criteria : searchCriteriaList)
		{
			if(criteria.getHidden() !=null && criteria.getHidden())
			{
				count --;
			}
		}
		
		return count;
		
	}

	private static void cleanUpSearchCriteria(List<SearchCriteria> searchCriteriaList) {
		
		List<SearchCriteria> invalidCriteriaList = new ArrayList<>(1);
		
		for(SearchCriteria criteria : searchCriteriaList)
		{
			if("".equals(checkNull(criteria.getFieldValue())) || "".equals(checkNull(criteria.getFieldName())))
			{
				invalidCriteriaList.add(criteria);
			}
		}
		
		searchCriteriaList.removeAll(invalidCriteriaList);
		
	}

	private static String getMapper(String fieldName) {
		
		String mapperName = "";
		if(fieldName != null)
		{
			mapperName = fieldName.trim().toLowerCase();
			mapperName = mapperName.replace(" ", "_");
			mapperName = ":"+mapperName;
		}
		return mapperName;
	}
	
	private static String checkNull(String value) {
		return value == null ? "": value;
	}

	
	public static void main(String[] args) throws TrackerException {
		
		
		String sql = "select * from product ";

		Map<String, String> joiner = new HashMap<String, String>();
		joiner.put("Project Name", "project.project_name");
		joiner.put("Project Type", "project.project_type");
		joiner.put("Release", "project.release");
		joiner.put("Start Date", "project.start_date");
		joiner.put("End Date", "project.end_date");
		joiner.put("Application Id", "project.application_id");
		joiner.put("User Id", "project.user_id");
		
		List<SearchCriteria> criList = new ArrayList<SearchCriteria>();
		
		SearchCriteria criteria = new SearchCriteria();
		criteria.setFieldName("Application Id");
		criteria.setFieldFilter("=");
		criteria.setFieldType("number");
		criteria.setFieldValue("1");
		criteria.setLogicalCdn("AND");
		criList.add(criteria);		
		
		criteria = new SearchCriteria();
		criteria.setFieldName("User Id");
		criteria.setFieldFilter("=");
		criteria.setFieldType("number");
		criteria.setFieldValue("1");
		criList.add(criteria);
		
		System.out.println(DynamicQueryGenerator.generateSearch(sql, joiner, criList));
		
		
	}

}
