package com.pikaso.home.cinemanote.exception;

/**
 * Response code: BAD_REQUEST (405)
 * @author pikaso
 *
 */
public class BadRequestException extends HttpStatusException {
	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) {
		super(message);
	}
}
