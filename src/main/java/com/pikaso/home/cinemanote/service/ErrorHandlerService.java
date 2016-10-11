package com.pikaso.home.cinemanote.service;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pikaso.home.cinemanote.exception.BadRequestException;
import com.pikaso.home.cinemanote.exception.GoneException;
import com.pikaso.home.cinemanote.exception.NotFoundException;
import com.pikaso.home.cinemanote.view.ErrorMessage;

@Api(name="Errors", description="Errors handler controller")
@RestController("/error/")
@ControllerAdvice
public class ErrorHandlerService {

	@ApiMethod(description="Handle NOT_FOUND statuses")
	@RequestMapping("/notfound")
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	@ApiResponseObject @ResponseBody 
	public ErrorMessage handleNotFound(Exception e) {
		return ErrorMessage.create(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	@ApiMethod(description="Handle BAD_REQUEST statuses")
	@RequestMapping("/badrequest")
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	@ApiResponseObject @ResponseBody 
	public ErrorMessage handleBadRequest(Exception e) {
		return ErrorMessage.create(HttpStatus.BAD_REQUEST, e.getMessage());
	}
	
	@ApiMethod(description="Handle GONE statuses")
	@RequestMapping("/gone")
	@ResponseStatus(value=HttpStatus.GONE)
	@ExceptionHandler(GoneException.class)
	@ApiResponseObject @ResponseBody 
	public ErrorMessage handleGone(Exception e) {
		return ErrorMessage.create(HttpStatus.GONE, "Bad server configuration. " + e.getMessage());
	}

}
