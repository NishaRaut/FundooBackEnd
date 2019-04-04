package com.bridgelabz.fundoo.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LoginDTO {
	@Email(regexp =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$",message="Not valid")
	@NotEmpty(message="Please fill the name")
	String email;
	
	@NotEmpty(message="Please fill the password")
String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
