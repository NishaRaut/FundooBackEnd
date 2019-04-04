package com.bridgelabz.fundoo.note.dto;

public class NoteDto {
	private String title;
	private String discription;
	private String color;
	private boolean isPinned;
	private boolean isArchive;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public boolean isInPinned() {
		return isPinned;
	}
	public void setInPinned(boolean inPinned) {
		this.isPinned = inPinned;
	}
	public boolean isArchive() {
		return isArchive;
	}
	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}
	
}
