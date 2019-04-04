package com.bridgelabz.fundoo.note.services;

import java.util.List;

import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.dto.NoteDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.response.Response;

public interface NoteService {
	public Response create(NoteDto noteDto, String token) throws Exception;
	public Response updateNote(NoteDto noteDto, String token, long noteId) throws Exception;
	public Response deleteNotePermanently(Long noteId, String token) throws Exception;
     List<Note> getAllNote(String token) throws Exception;
    Note getNote(String token, Long noteId) throws Exception;
    public Response pinnedUnpinned(String token, Long noteId) throws Exception;
    public Response trashedUntrashed(String token, Long noteId) throws Exception;
    public Response archiveUnarchive(String token, Long noteId) throws Exception;
    Response addLabel(String userToken, long noteId,  LabelDto labelDTO);
    Response removeLabel(String userToken, long noteId, LabelDto labelDTO);
}
