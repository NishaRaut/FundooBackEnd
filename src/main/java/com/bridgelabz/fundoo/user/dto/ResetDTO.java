package com.bridgelabz.fundoo.user.dto;

import javax.validation.constraints.NotEmpty;

public class ResetDTO {
	@NotEmpty(message="Please fill the password")
	String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "ResetDTO [password=" + password + "]";
	}
	
}
