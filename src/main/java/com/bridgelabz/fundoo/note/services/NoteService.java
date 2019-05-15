package com.bridgelabz.fundoo.note.services;

import java.text.ParseException;
import java.util.List;

import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.dto.NoteDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;

public interface NoteService {
	Response create(NoteDto noteDto, String token) ;

	Response updateNote(NoteDto noteDto, String token, long noteId);

	Response deleteNotePermanently(Long noteId, String token) ;

	List<Note> getAllNote(String token,boolean archived,boolean trashed) ;

	Note getNote(String token, Long noteId) ;

	Response pinnedUnpinned(String token, Long noteId);

	Response trashedUntrashed(String token, Long noteId);

	Response archiveUnarchive(String token, Long noteId) ;

	Response addLabelToNote(String userToken, long noteId, long labelId);

	Response removeLabelFromNote(String userToken, long noteId, long labelId);

	Response ReminderSet(long noteId, String time,String token)throws ParseException ;

	Response ReminderRemove(long noteId,String token)throws ParseException;

	Response colorSet(long noteId, String color, String token);
	
	Response addCollaboratedUser(long noteId, String email, String token);
	
	Response removeCollaboratedUser(long noteId, String email, String token);
	
	List<User> getCollabNote(String token,long noteId);
	
    List<Note> searchNote(String query,String token);
}
