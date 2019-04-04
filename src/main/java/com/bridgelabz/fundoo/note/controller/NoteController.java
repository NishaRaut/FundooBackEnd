package com.bridgelabz.fundoo.note.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.dto.NoteDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.services.NoteService;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.utility.ResponseInfo;


@RestController
public class NoteController 
{
static final Logger logger = LoggerFactory.getLogger(NoteController.class);
@Autowired 
private NoteService noteServices;
@Autowired
Environment environment;

@Autowired
Response response;

@PostMapping("/createNote")
public ResponseEntity<Response> Create(@RequestBody NoteDto noteDto,@RequestHeader String token) throws Exception
{
	System.out.println("ashjdghasgdhjasdgfhjasgf");
	logger.info("Note DTO:"+noteDto);
	logger.trace("Create note:");
	response = noteServices.create(noteDto,token); 
	return new ResponseEntity<>(response, HttpStatus.OK);
	
}
@PutMapping("/updateNote/{noteId}")
public ResponseEntity<Response> UpdateNote(@RequestBody NoteDto noteDto, @RequestHeader String token, @PathVariable long noteId) throws Exception
{
	logger.info("Note DTO:"+noteDto);
	logger.trace("Update note:");
	response = noteServices.updateNote(noteDto, token, noteId);
	return new ResponseEntity<>(response, HttpStatus.OK);
}
@DeleteMapping("/deleteNote")
public ResponseEntity<Response> deleteNotePermanently(@RequestBody Long noteId, @RequestHeader String token) throws Exception
{
	logger.info("Note Id:"+noteId);
	logger.trace("Delete note:");
	response = noteServices.deleteNotePermanently(noteId, token);
	return new ResponseEntity<>(response, HttpStatus.OK);
}
@GetMapping("/allNotes")
public ResponseEntity<List<Note>> getAllNotes(@RequestHeader String token) throws Exception
{
	logger.info("Token:"+token);
	logger.info("Get all notes:");
	List<Note> allNotes=noteServices.getAllNote(token);
	return new ResponseEntity<>(allNotes, HttpStatus.OK);
	
}
@GetMapping("/note/{noteId}")
public Note getNote(@RequestHeader String token,@PathVariable Long noteId)throws Exception
{
	logger.info("Token:"+token);
	logger.info("Note Id:"+noteId);
	logger.trace("Get note by id:");
Note note = noteServices.getNote(token, noteId);
	return note;
}
@PutMapping("/pinNote/{noteId}")
public ResponseEntity<Response> pinnedUnpinned(@RequestHeader String token, @PathVariable Long noteId) throws Exception
{
  logger.info("Token:"+token);
  logger.info("Note Id:"+noteId);
  logger.trace("Pinned/Unpinned by id:");
response = noteServices.pinnedUnpinned(token, noteId);
return new ResponseEntity<>(response, HttpStatus.OK);

}
@PutMapping("/trashNote/{noteId}")
public ResponseEntity<Response> trashedUntrashed(@RequestHeader String token, @PathVariable Long noteId) throws Exception
{
logger.info("Token:"+token);
logger.info("Note Id:"+noteId);
logger.trace("Trashed/Untrashed by id:");
response = noteServices.trashedUntrashed(token, noteId);
return new ResponseEntity<>(response, HttpStatus.OK);

}
@PutMapping("/archiveNote/{noteId}")
public ResponseEntity<Response> archiveUnarchive(@RequestHeader String token, @PathVariable Long noteId) throws Exception
{
logger.info("Token:"+token);
logger.info("Note Id:"+noteId);
logger.trace("Trashed/Untrashed by id:");
response = noteServices.archiveUnarchive(token, noteId);
return new ResponseEntity<>(response, HttpStatus.OK);
}

   //add label to the note
   // remove label from the note
@PostMapping("/{noteId}/labels")
public ResponseEntity<Response> addLabel(@RequestHeader String token, @PathVariable long noteId, @RequestBody LabelDto labelDTO){
	logger.info("token: " + token);
	logger.trace("add label to note");
	Response response;
	if(labelDTO.getName().equals("") || labelDTO.getName() == null)
		response = ResponseInfo.getResponse(-800, "label can't not be empty");
	else
		response = noteServices.addLabel(token, noteId, labelDTO);
	return new ResponseEntity<>(response,HttpStatus.OK);
}

@PutMapping(value = "/notes/{noteId}/labels")
public ResponseEntity<Response> removeLabel(@RequestHeader String token, @PathVariable long noteId, @RequestBody LabelDto labelDTO){
	logger.info("token: " + token);
	logger.trace("remove label to note");
	Response response;
	if(labelDTO.getName().equals("") || labelDTO.getName() == null)
		response = ResponseInfo.getResponse(-800, "label can't not be empty");
	else
		response = noteServices.removeLabel(token, noteId, labelDTO);
	return new ResponseEntity<>(response,HttpStatus.OK);
}
}

