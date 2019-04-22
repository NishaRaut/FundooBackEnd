package com.bridgelabz.fundoo.user.dto;

import javax.validation.constraints.NotEmpty;

public class ResetDTO {
	@NotEmpty(message="Please fill the password")
	String password;
	String  confirmPassword;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	

	
}
