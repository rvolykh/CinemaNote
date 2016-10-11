package com.pikaso.home.cinemanote.exception;

/**
 * Response code: GONE (410)
 * @author pikaso
 */
public class GoneException extends HttpStatusException {
	private static final long serialVersionUID = 1L;

	public GoneException(String message) {
		super(message);
	}
}
