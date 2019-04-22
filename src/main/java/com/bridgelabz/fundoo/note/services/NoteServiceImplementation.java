package com.bridgelabz.fundoo.note.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.LabelRepository;
import com.bridgelabz.fundoo.note.repository.NoteRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.utility.ResponseInfo;
import com.bridgelabz.fundoo.utility.UserToken;

@SuppressWarnings("unused")
@Service
@PropertySource("classpath:message.properties")
public class NoteServiceImplementation implements NoteService {

	public static final Logger logger = LoggerFactory.getLogger("NoteServicesImplementation.class");

	@Autowired
	private NoteRepository noteRepository;
	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	Environment environment;
//	@Autowired
//	Response response;

	@Autowired
	UserToken userToken;

	@Override
	public Response create(NoteDto noteDto, String token) throws Exception {
		logger.info("Note DTO:" + noteDto);
		logger.info("Token:" + token);
		logger.trace("Create a note");
		Long userId = userToken.tokenVerify(token);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new Exception(environment.getProperty("user not found")));
		Note note = modelMapper.map(noteDto, Note.class);
		note.setUser(user);
		// note.setUserId(userId);
		// Optional<User> user = userRepository.findById(userId);
		// user.get().getNotes().add(note);
		// return null;
		// noteRepository.save(note);
		note.setCreateDate(LocalDateTime.now());
		note.setModifyDate(LocalDateTime.now());
		note = noteRepository.save(note);

		Response response = ResponseInfo.getResponse(
				Integer.parseInt(environment.getProperty("status.createNote.successCode")),
				environment.getProperty("status.createNote.successMessage"));
		return response;

	}

	@Override
	public Response updateNote(NoteDto noteDto, String token, long noteId) throws Exception {
		System.out.println("djkhfdkjsgfhdgh");
		long userId = userToken.tokenVerify(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new Exception(environment.getProperty("This User does not found")));
		if (!user.isVerification())
			throw new UserException(environment.getProperty("status.login.unVerifiedUser"),
					Integer.parseInt(environment.getProperty("status.user.errorCode")));
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteException(environment.getProperty("status.id.error"),101));
		modelMapper.map(noteDto, note);
		note.setModifyDate(LocalDateTime.now());
		note = noteRepository.save(note);
		if (note == null)
			throw new NoteException(environment.getProperty("status.updateNote.failedMessage"),
					Integer.parseInt(environment.getProperty("status.note.update.errorMessage")));
		Response response = ResponseInfo.getResponse(
				Integer.parseInt(environment.getProperty("status.note.update.successCode")),
				environment.getProperty("status.updateNote.successMessage"));
		return response;
	}

	@Override
	public Response deleteNotePermanently(Long noteId, String token) throws Exception {
		long userId = userToken.tokenVerify(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new Exception(environment.getProperty("This User does not found")));
		if (!user.isVerification())
			throw new UserException(environment.getProperty("This note does not found"),
					Integer.parseInt(environment.getProperty("status.user.errorCode")));
		noteRepository.deleteById(noteId);
		if (noteRepository.findById(noteId).isPresent())
			throw new NoteException(environment.getProperty("status.note.delete.errorMessage"),
					Integer.parseInt(environment.getProperty("status.note.delete.errorCode")));
		Response response = ResponseInfo.getResponse(
				Integer.parseInt(environment.getProperty("status.deleteNote.successCode")),
				environment.getProperty("status.deleteNote.successMessage"));
		return response;
	}

	@Override
	public List<Note> getAllNote(String token) throws Exception {
		long userId = userToken.tokenVerify(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new Exception(environment.getProperty("This User does not found")));
		List<Note> allNotes = noteRepository.findByUser(user);
		return allNotes;

	}

	@Override
	public Note getNote(String token, Long noteId) throws Exception {
		long userId = userToken.tokenVerify(token);
		userRepository.findById(userId)
				.orElseThrow(() -> new Exception(environment.getProperty("This User does not found")));
		Optional<Note> note = noteRepository.findById(noteId);
		if (!note.isPresent())
			throw new NoteException(environment.getProperty("status.note.getting.errorMessage"),
					Integer.parseInt(environment.getProperty("status.getNote.errorCode")));
		return note.get();
	}

	@Override
	public Response pinnedUnpinned(String token, Long noteId) throws Exception {
		Response response;
		System.out.println("pin");
		Long userId = userToken.tokenVerify(token);
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new Exception(environment.getProperty("This note does not found")));
//		if (note.getUser().getId() != userId) {
//			throw new Exception("User not a match");
//		}
		
		System.out.println("pin34");
		if (note.isPinned()) {
			note.setPinned(false);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.unpinnedcode")),
					environment.getProperty("status.note.unpinnedMessage"));
		} else {
			note.setPinned(true);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.pinnedcode")),
					environment.getProperty("status.note.pinnedMessage"));
		}
		note = noteRepository.save(note);
		return response;
	}

	@Override
	public Response trashedUntrashed(String token, Long noteId) throws Exception {
		Response response;
	   Long userId = userToken.tokenVerify(token);
	   User user=userRepository.findById(userId).get();
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteException(environment.getProperty("status.id.error"),100));
//		if (note.getUser().getId() != userId) {
//			throw new Exception("User not a match");
//		}
		if (note.isTrash()) {
			note.setTrash(false);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.untrashedCode")),
					environment.getProperty("status.note.untrashedMessage"));
		} else {
			note.setTrash(true);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.trashedCode")),
					environment.getProperty("status.note.trashedMessage"));
		}
		note = noteRepository.save(note);
		return response;
	}
    @Override
	public Response archiveUnarchive(String token, Long noteId) throws Exception {
		Response response;
		//Long userId = userToken.tokenVerify(token);
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new Exception(environment.getProperty("This note does not found")));
		if (note.isArchive()) {
			note.setArchive(false);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.unarchiveCode")),
					environment.getProperty("status.note.unarchiveMessage"));
		} else {
			note.setArchive(true);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.archiveCode")),
					environment.getProperty("status.note.archiveMessage"));
		}
		note = noteRepository.save(note);
		return response;
	}
	
	

	@Override
	public Response addLabelToNote(String userToken, long noteId, long labelId) {
		
		//System.out.println("hello############");
		//long userId=UserToken.tokenVerify(token);
		
		Note note=noteRepository.findById(noteId).get();
		Label label=labelRepository.findById(labelId).get();
		note.getLabels().add(label);
		//label.getNotes().add(note);
		label.getNotes().add(note);
		// stream the list and use the set to filter it
		List<Label> labels = note.getLabels().stream()
		            .filter(e -> (e.getLableId()==labelId))
		            .collect(Collectors.toList());
		
		
		System.out.println(labels);
		noteRepository.save(note);

	    Response response=ResponseInfo.getResponse(401,"label successfully added");
	    return response;
	    //return null;
	}

	@Override
	public Response removeLabelFromNote(String userToken, long noteId,long labelId) {
		System.out.println("hello############");	
		return null;
	}


	

	@Override
	public Response ReminderSet(long noteId, String time) throws ParseException {
		System.out.println("33333");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		  Date date =dateFormat.parse(time);
		  System.out.println("33333444");
		  System.out.println(noteId);
		 Note note=noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("ha"));
		 //Note note=noteRepository.findById(noteId).get();
		 System.out.println(noteId);
		  System.out.println(note);
		  System.out.println("33333666");
		  note.setReminder(date);
		  noteRepository.save(note);
		  Response response = ResponseInfo.getResponse(400,"Set reminder successfully");
		return response;
	}

	@Override
	public Response ReminderRemove(long noteId) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		 // Date date =dateFormat.parse(time);
		  Note note=noteRepository.findById(noteId).get();
		  System.out.println(note);
		  note.setReminder(null);
		  noteRepository.save(note);
		  Response response = ResponseInfo.getResponse(400,"remove reminder successfully");
		return response;
	}

	@Override
	public Response colorSet(long noteId, String color, String token) throws Exception {
		Response response;
		   Long userId = userToken.tokenVerify(token);
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteException(environment.getProperty("id not found"),101));
		note.setColor(color);
		noteRepository.save(note);
		  response = ResponseInfo.getResponse(400,"Set color successfully");
		return response;
	}
	
}
