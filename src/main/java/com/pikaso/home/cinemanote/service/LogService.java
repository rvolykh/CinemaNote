package com.pikaso.home.cinemanote.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pikaso.home.cinemanote.exception.GoneException;

/**
 * Download log file service
 * @author pikaso
 */
@Controller
@Api(name = "Log service", description = "Manage application logs")
public class LogService {

	@Value("${log.file.path}")
	private String logFile;

	@ApiMethod(description="Download log file")
	@RequestMapping(value="/log", method = RequestMethod.GET)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<?> getLogs(HttpServletResponse response) {

		File file = new File(logFile);
		
		try (InputStream inputStream = new FileInputStream(file)) {
			StreamUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception e) {
			throw new GoneException(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
	}
	
	@ApiMethod(description="Clean log file")
	@RequestMapping(value="/log", method = RequestMethod.DELETE)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<?> cleanLogs(HttpServletResponse response) {

		File file = new File(logFile);
		
		try (OutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write("".getBytes());
		} catch (Exception e) {
			throw new GoneException(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
	}
}
