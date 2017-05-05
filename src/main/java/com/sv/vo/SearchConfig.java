package com.sv.vo;

public class SearchConfig {
	
	private int searchConfigId;
	private String searchOn;
	private String fieldName;
	private String fieldType;
	
	public int getSearchConfigId() {
		return searchConfigId;
	}
	public void setSearchConfigId(int searchCriteriaId) {
		this.searchConfigId = searchCriteriaId;
	}
	public String getSearchOn() {
		return searchOn;
	}
	public void setSearchOn(String searchOn) {
		this.searchOn = searchOn;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	
}
