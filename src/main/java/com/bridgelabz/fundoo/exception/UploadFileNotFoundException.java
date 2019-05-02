package com.bridgelabz.fundoo.exception;

public class UploadFileNotFoundException extends RuntimeException{
	 public UploadFileNotFoundException(String message) {
	        super(message);
	    }

	    public UploadFileNotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
