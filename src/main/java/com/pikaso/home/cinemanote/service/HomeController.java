package com.pikaso.home.cinemanote.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Basic controller for UI
 * @author pikaso
 */
@Controller
public class HomeController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

}
