package com.bridgelabz.fundoo.note.dto;

import javax.validation.constraints.NotNull;

public class LabelDto {
	@NotNull
    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
