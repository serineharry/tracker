package com.sv.framework.exceptions;

public class TrackerException extends Exception {

	private static final long serialVersionUID = -7987978270260338068L;
	
	private String errorCode;
	private String errorMessage;
	
	public TrackerException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
		
	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String toString() {
		
		return (this.errorCode + " " + this.errorMessage);
	}


	

}
