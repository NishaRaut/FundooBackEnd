package com.bridgelabz.fundoo.elasticserach;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.model.Note;

@Service
public interface ElasticSearch {
	public Note create(Note note) throws IOException;
	public Note updateNote(Note note);
	public void deleteNote(Long NoteId);
	public List<Note> searchData(String query,long userId);
}
