package com.pikaso.home.cinemanote.exception;

/**
 * Main Error handler
 * @author pikaso
 */
public class HttpStatusException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public HttpStatusException(String message) {
		super(message);
	}
}
