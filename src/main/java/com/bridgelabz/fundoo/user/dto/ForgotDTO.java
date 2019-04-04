package com.bridgelabz.fundoo.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ForgotDTO {
	@Email(regexp =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$",message="Not valid")
	@NotEmpty(message="Please fill the name")
	String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ForgotDTO [email=" + email + "]";
	}
	
	
}
