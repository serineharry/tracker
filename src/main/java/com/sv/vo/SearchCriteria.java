package com.sv.vo;

public class SearchCriteria {

    private int searchCriteriaId;
    private String fieldName;
    private String fieldFilter;
    private String fieldValue;
    private String logicalCdn;
    private String fieldType;
    private Boolean hidden;
    
	public int getSearchCriteriaId() {
		return searchCriteriaId;
	}
	
	public void setSearchCriteriaId(int searchCriteriaId) {
		this.searchCriteriaId = searchCriteriaId;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldFilter() {
		return fieldFilter;
	}
	
	public void setFieldFilter(String fieldFilter) {
		this.fieldFilter = fieldFilter;
	}
	
	public String getFieldValue() {
		return fieldValue;
	}
	
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	public String getLogicalCdn() {
		return logicalCdn;
	}
	
	public void setLogicalCdn(String logicalCdn) {
		this.logicalCdn = logicalCdn;
	}
	
	public String getFieldType() {
		return fieldType;
	}
	
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}	
    
}
