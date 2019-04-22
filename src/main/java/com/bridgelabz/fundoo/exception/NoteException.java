package com.bridgelabz.fundoo.exception;

public class NoteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	int errorCode;
	public NoteException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	public int getErrorCode() {
		return errorCode;
	}
}
