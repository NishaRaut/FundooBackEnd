package com.bridgelabz.fundoo.exception;

public class TokenException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	int errorCode;
	public TokenException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	public int getErrorCode() {
		return errorCode;
	}
}
