package com.bridgelabz.fundoo.exception;

public class UserException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	int errorCode;
	public UserException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	public int getErrorCode() {
		return errorCode;
	}
}
