package com.bridgelabz.fundoo.exception;
import com.bridgelabz.fundoo.utility.ResponseInfo;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bridgelabz.fundoo.response.Response;

@RestControllerAdvice
public class FundooExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> globelExceptionHandler(Exception e)
	{
		Response statusInfo = ResponseInfo.getResponse(-200,"Internal Error");
		return new ResponseEntity<>(statusInfo,HttpStatus.OK);
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> UserExceptionHandler(UserException e)
	{
		Response response = ResponseInfo.getResponse(e.getErrorCode(), e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.OK);	
	}

	@ExceptionHandler(TokenException.class)
	public ResponseEntity<Response> tokenExceptionHandler(TokenException e)
	{
		Response statusInfo = ResponseInfo.getResponse(e.getErrorCode(),e.getMessage());
		return new ResponseEntity<>(statusInfo,HttpStatus.OK);	
	}
	@ExceptionHandler(EmailException.class)
	public ResponseEntity<Response> EmailExceptionHandler(EmailException e)
	{
		Response statusInfo = ResponseInfo.getResponse(e.getErrorCode(),e.getMessage());
		return new ResponseEntity<>(statusInfo,HttpStatus.OK);	
	}
	@ExceptionHandler(NoteException.class)
	public ResponseEntity<Response> NoteExceptionHandler(NoteException e)
	{
		Response statusInfo = ResponseInfo.getResponse(e.getErrorCode(),e.getMessage());
		return new ResponseEntity<>(statusInfo,HttpStatus.OK);	
	}
}
