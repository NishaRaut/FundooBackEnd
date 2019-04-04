package com.bridgelabz.fundoo.user.controllers;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;



import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.ForgotDTO;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.ResetDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.services.UserServices;


@RestController 
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*",exposedHeaders={"jwtTokens"})
public class UserController {
	
	static final Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserServices userServices;

	@PostMapping("/register")
	public ResponseEntity<Response> userRegister(@Valid @RequestBody UserDTO userDTO) throws Exception {
		
		logger.info("userDTO:"+userDTO);
		logger.trace("User Registration");
		System.out.println("Hello"+userDTO);
		Response response = userServices.register(userDTO);
		return new ResponseEntity<Response>(response , HttpStatus.OK);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<ResponseToken> login(@Valid @RequestBody LoginDTO loginDTO, 
			HttpServletResponse httpresponse) throws Exception {
		
		logger.info("loginDTO:" + loginDTO);
		logger.trace("Login");
		System.out.println(loginDTO.getEmail());
		System.out.println(loginDTO.getPassword());
		ResponseToken response = userServices.login(loginDTO, httpresponse);
		return new ResponseEntity<ResponseToken>(response, HttpStatus.OK);
	}
	
	
	
    @GetMapping("userActivation/{token}")
	public String emailValidation(@PathVariable String token) throws Exception
	{
    	System.out.println("in verify");
		logger.info("Token:"+token);
		logger.trace("User Verification");
		String result = userServices.validateEmailId(token);
		return result;
	}
	
	@PostMapping("/forgetPassword")
	public ResponseEntity<Response> forgetPassword(@RequestBody ForgotDTO email) throws Exception
	{
	/*	logger.info("email:"+loginDTO.getEmail());
		logger.trace("Forget Password");*/
		Response response = userServices.forgotPassword(email.getEmail());
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	@PutMapping("resetpassword/{token}")
	public ResponseEntity<Response> resetPassword(@RequestBody ResetDTO password,  @PathVariable String token) throws Exception{
		logger.info("user password : " + password.getPassword());
		logger.trace("reset user password");

		Response response = userServices.resetPassword(password.getPassword(), token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
}

}

