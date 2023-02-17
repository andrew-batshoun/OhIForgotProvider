package com.org.OhIForgotProvider.exceptions;

public abstract class ResponseEntityException extends RuntimeException {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResponseEntityException(String message) {
		super(message);
	}
}
