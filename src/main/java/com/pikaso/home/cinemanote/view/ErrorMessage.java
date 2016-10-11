package com.pikaso.home.cinemanote.view;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * Error message for responses
 * @author pikaso
 */
@Getter
@ApiObject(name="ErrorMessage", description="Response for errors", visibility=ApiVisibility.PUBLIC)
public class ErrorMessage {
	private ErrorMessage(){}
	
	@ApiObjectField(description = "the error code")
	private int code;
	@ApiObjectField(description = "the error message")
	private String message;
	
	public static ErrorMessage create(HttpStatus httpStatus, String message){
		ErrorMessage result = new ErrorMessage();
		result.code = httpStatus.value();
		result.message = message;
		
		return result;
	}
}
