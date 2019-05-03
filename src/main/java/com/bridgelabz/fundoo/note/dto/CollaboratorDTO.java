package com.bridgelabz.fundoo.note.dto;

import java.io.Serializable;

public class CollaboratorDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String email;

	public CollaboratorDTO() {

	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
