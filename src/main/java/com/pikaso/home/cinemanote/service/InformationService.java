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
import com.pikaso.home.cinemanote.view.LanguageDTO;

@RestController
@Api(name = "Information service", description = "Provide additional useful information")
@RequestMapping(value = "/information", produces = MediaType.APPLICATION_JSON_VALUE)
public class InformationService {
	
	@Autowired
	private InformationManager informationManager;
	
	@ApiMethod(description="Find available languages")
	@RequestMapping(value="/languages", method = RequestMethod.GET)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<LanguageDTO[]> availableLanguages(){
		
		return ResponseEntity.ok().body(informationManager.getLanguages());
	}
	
	@ApiMethod(description="Find available roles")
	@RequestMapping(value="/roles", method = RequestMethod.GET)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<String[]> availableRoles(){
		
		return ResponseEntity.ok().body(informationManager.getRoles());
	}
	
	@ApiMethod(description="Find available friend filters")
	@RequestMapping(value="/friend/filter", method = RequestMethod.GET)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<String[]> availableFriendFilters(){
		
		return ResponseEntity.ok().body(informationManager.getFriendFilters());
	}
}
