package com.bridgelabz.fundoo.utility;

import com.bridgelabz.fundoo.note.model.Note;

public class NoteContainer {
	private Note note;
	private NoteOperation noteoperation;
	public Note getNote() {
		return note;
	}
	public void setNote(Note note) {
		this.note = note;
	}
	public NoteOperation getNoteoperation() {
		return noteoperation;
	}
	public void setNoteoperation(NoteOperation noteoperation) {
		this.noteoperation = noteoperation;
	}
	
}
