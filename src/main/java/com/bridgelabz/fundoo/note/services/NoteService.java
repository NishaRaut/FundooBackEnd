package com.bridgelabz.fundoo.note.services;

import java.text.ParseException;
import java.util.List;

import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.dto.NoteDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.response.Response;

public interface NoteService {
	Response create(NoteDto noteDto, String token) throws Exception;

	Response updateNote(NoteDto noteDto, String token, long noteId) throws Exception;

	Response deleteNotePermanently(Long noteId, String token) throws Exception;

	List<Note> getAllNote(String token) throws Exception;

	Note getNote(String token, Long noteId) throws Exception;

	Response pinnedUnpinned(String token, Long noteId) throws Exception;

	Response trashedUntrashed(String token, Long noteId) throws Exception;

	Response archiveUnarchive(String token, Long noteId) throws Exception;

	Response addLabelToNote(String userToken, long noteId, long labelId);

	Response removeLabelFromNote(String userToken, long noteId, long labelId);

	Response ReminderSet(long noteId, String time) throws ParseException;

	Response ReminderRemove(long noteId)throws ParseException;

	Response colorSet(long noteId, String color, String token)throws Exception;
}
