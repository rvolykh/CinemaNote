package com.pikaso.home.cinemanote.service;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pikaso.home.cinemanote.manager.InformationManager;
import com.pikaso.home.cinemanote.view.PingDTO;

/**
 * Simple ping service
 * @author pikaso
 */
@RestController
@Api(description = "The ping controller", name = "Ping service")
public class HealthService {

	@Autowired
	private InformationManager informationManager;

	/**
	 * Ping method
	 * @return the answer number and server date
	 */
	@ApiMethod(description="Simple ping method")
	@RequestMapping(value="/health", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<PingDTO> ping() {

		return ResponseEntity.ok().body(informationManager.health()); 
	}
}
