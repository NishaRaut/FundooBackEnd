package com.bridgelabz.fundoo.note.services;

import java.time.LocalDateTime;


import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.dto.NoteDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.utility.ResponseInfo;
import com.bridgelabz.fundoo.utility.UserToken;



@Service
@PropertySource("classpath:message.properties")
public class NoteServiceImplementation implements NoteService {

	public static final Logger logger = LoggerFactory.getLogger("NoteServicesImplementation.class"); 

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	Environment environment;
	@Autowired
	Response response;

	@Autowired
	UserToken userToken;



	@Override
	public Response create(NoteDto noteDto, String token) throws Exception {
		logger.info("Note DTO:"+noteDto);
		logger.info("Token:"+token);
		logger.trace("Create a note");
		Long userId =userToken.tokenVerify(token);

		User user = userRepository.findById(userId).orElseThrow(() -> new Exception(environment.getProperty("user not found")));
		Note note = modelMapper.map(noteDto,Note.class);
		note.setUser(user);
		// note.setUserId(userId);
		//Optional<User> user = userRepository.findById(userId);
		// user.get().getNotes().add(note);
		//return null;
		// noteRepository.save(note);
		note.setCreateDate(LocalDateTime.now());
		note.setModifyDate(LocalDateTime.now());
		note = noteRepository.save(note);

		response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.createNote.successCode")),
				environment.getProperty("status.createNote.successMessage"));
		return response;

	}



	@Override
	public Response updateNote(NoteDto noteDto, String token, long noteId) throws Exception {
		System.out.println("djkhfdkjsgfhdgh");
		long userId=userToken.tokenVerify(token);
		User user = userRepository.findById(userId).orElseThrow(() -> new Exception(environment.getProperty("This User does not found")));
		if(!user.isVerification())
			throw new UserException(environment.getProperty("status.login.unVerifiedUser"),Integer.parseInt(environment.getProperty("status.user.errorCode")));
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new Exception(environment.getProperty("This note does not found")));
		modelMapper.map(noteDto, note);
		note.setModifyDate(LocalDateTime.now());
		note = noteRepository.save(note);
		if(note == null)
			throw new NoteException(environment.getProperty("status.updateNote.failedMessage"), Integer.parseInt(environment.getProperty("status.note.update.errorMessage")));
		response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.update.successCode")),
				environment.getProperty("status.updateNote.successMessage"));
		return response;
	}

	
	@Override
	public Response deleteNotePermanently(Long noteId, String token) throws Exception {
		// TODO Auto-generated method stub
		long userId=userToken.tokenVerify(token);
		User user = userRepository.findById(userId).orElseThrow(() -> new Exception(environment.getProperty("This User does not found")));
		if(!user.isVerification())
			throw new UserException(environment.getProperty("This note does not found"),Integer.parseInt(environment.getProperty("status.user.errorCode")));
		 noteRepository.deleteById(noteId);
	    if(noteRepository.findById(noteId).isPresent())
	    	throw new NoteException(environment.getProperty("status.note.delete.errorMessage"),Integer.parseInt(environment.getProperty("status.note.delete.errorCode")));
		response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.deleteNote.successCode")),
		environment.getProperty("status.deleteNote.successMessage"));
		return response;
	}



	@Override
	public List<Note> getAllNote(String token) throws Exception {
		long userId=userToken.tokenVerify(token);
		User user = userRepository.findById(userId).orElseThrow(() -> new Exception(environment.getProperty("This User does not found")));
		List<Note> allNotes = noteRepository.findByUser(user);
		return allNotes;
	
	}

	@Override
	public Note getNote(String token, Long noteId) throws Exception {
		long userId = userToken.tokenVerify(token);
        userRepository.findById(userId).orElseThrow(() -> new Exception(environment.getProperty("This User does not found")));
		Optional<Note> note = noteRepository.findById(noteId);
		if(!note.isPresent())
			throw new NoteException(environment.getProperty("status.note.getting.errorMessage"), Integer.parseInt(environment.getProperty("status.getNote.errorCode")));
			return note.get();
	}

	@Override
	public Response pinnedUnpinned(String token, Long noteId) throws Exception  {
		Response response;
		Long userId = userToken.tokenVerify(token);
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new Exception(environment.getProperty("This note does not found")));
		if (note.getUser().getId() != userId) {
			throw new Exception("User not a match");
        }
		if (note.isPinned()) {
			note.setPinned(false);
			response =ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.unpinnedcode")),
					environment.getProperty("status.note.unpinnedMessage"));
		} else {
			note.setPinned(true);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.pinnedcode")),
					environment.getProperty("status.note.pinnedMessage"));
		}
		note =	noteRepository.save(note);
        return response;
	}



	@Override
	public Response trashedUntrashed(String token, Long noteId) throws Exception {
		Response response;
		Long userId = userToken.tokenVerify(token);
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new Exception(environment.getProperty("This note does not found")));
		if (note.getUser().getId() != userId) {
			throw new Exception("User not a match");
        }
		if (note.isTrash()) {
			note.setTrash(false);
			response =ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.untrashedCode")),
					environment.getProperty("status.note.untrashedMessage"));
		} else {
			note.setTrash(true);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.trashedCode")),
					environment.getProperty("status.note.trashedMessage"));
		}
		note =	noteRepository.save(note);
        return response;
	}



	@Override
	public Response archiveUnarchive(String token, Long noteId) throws Exception {
		Response response;
		Long userId = userToken.tokenVerify(token);
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new Exception(environment.getProperty("This note does not found")));
		if (note.getUser().getId() != userId) {
			throw new Exception("User not a match");
        }
		if (note.isArchive()) {
			note.setArchive(false);
			response =ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.unarchiveCode")),
					environment.getProperty("status.note.unarchiveMessage"));
		} else {
			note.setArchive(true);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.archiveCode")),
					environment.getProperty("status.note.archiveMessage"));
		}
		note =	noteRepository.save(note);
        return response;
	}



	@Override
	public Response addLabel(String userToken, long noteId, LabelDto labelDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Response removeLabel(String userToken, long noteId, LabelDto labelDTO) {
		// TODO Auto-generated method stub
		return null;
	}
}
