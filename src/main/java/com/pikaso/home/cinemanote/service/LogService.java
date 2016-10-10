package com.pikaso.home.cinemanote.service;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pikaso.home.cinemanote.manager.LogManager;

/**
 * NOT IMPLEMENTED YET
 * @author pikaso
 */
@RestController
@Api(name = "Log service", description = "Receive application logs")
public class LogService {

	@Autowired
	private LogManager logManager;

	@ApiMethod(description="Find logs")
	@RequestMapping(value="/log", method = RequestMethod.GET)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<Void> findLogs(@ApiQueryParam(name = "from", description = "from date")
			@RequestParam(required = false) Long from, @ApiQueryParam(name = "to", description = "to date")
			@RequestParam(required = false) Long to) {
		
		logManager.client();
		logManager.server();

		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}
}
