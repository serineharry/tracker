package com.sv.framework;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sv.framework.exceptions.TrackerException;

public class TrackerControllerFactory {
	
	
	@ExceptionHandler(TrackerException.class)
	@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
	public RestErrorResponse handleException(TrackerException ex){
		return new RestErrorResponse(ex.getErrorCode(), ex.getErrorMessage());				
	}

}
