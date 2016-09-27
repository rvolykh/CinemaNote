package com.pikaso.home.cinemanote.exception;

/**
 * Response code: NOT_FOUND 
 * @author pikaso
 *
 */
public class NotFoundException extends HttpStatusException {
	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(message);
	}
}
