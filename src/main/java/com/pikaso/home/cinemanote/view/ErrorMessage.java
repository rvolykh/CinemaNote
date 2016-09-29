package com.pikaso.home.cinemanote.view;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.pojo.ApiVisibility;

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
	
	public static ErrorMessage create(Code code, String message){
		ErrorMessage result = new ErrorMessage();
		result.code = code.getValue();
		result.message = message;
		
		return result;
	}
	
	public enum Code {
		NONE_EXISTING_ID(101), NOT_FOUND(102), BAD_REQUEST(103);
		
		private final int code;
		
		private Code(int code){
			this.code = code;
		}
		
		public int getValue(){
			return this.code;
		}
	}
}
