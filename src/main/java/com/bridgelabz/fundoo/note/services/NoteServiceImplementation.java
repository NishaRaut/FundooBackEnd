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
	private Environment environment;
//	@Autowired
//	Response response;

	@Autowired
	private UserToken userToken;

	@Override
	public Response create(NoteDto noteDto, String token) {
		logger.info("Note DTO:" + noteDto);
		logger.info("Token:" + token);
		logger.trace("Create a note");
		Long userId = userToken.tokenVerify(token);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(environment.getProperty("status.user.errorMessage"),
						Integer.parseInt(environment.getProperty("status.user.errorCode"))));
		Note note = modelMapper.map(noteDto, Note.class);
		note.setUser(user);
		note.setCreateDate(LocalDateTime.now());
		note.setModifyDate(LocalDateTime.now());
		note = noteRepository.save(note);
		if (note == null) {
			throw new NoteException(environment.getProperty("status.note.errorCode"),
					Integer.parseInt(environment.getProperty("status.note.errorMessage")));
		}
		Response response = ResponseInfo.getResponse(
				Integer.parseInt(environment.getProperty("status.note.successCode")),
				environment.getProperty("status.note.successsMessage"));
		return response;

	}

	@Override
	public Response updateNote(NoteDto noteDto, String token, long noteId) {
		System.out.println("djkhfdkjsgfhdgh");
		long userId = userToken.tokenVerify(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(environment.getProperty("status.user.errorMessage"),
						Integer.parseInt(environment.getProperty("status.user.errorCode"))));

//				if (!user.isVerification())
//			throw new UserException(environment.getProperty("status.login.unVerifiedUser"),
//					Integer.parseInt(environment.getProperty("status.user.errorCode")));
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteException(environment.getProperty("status.noteId.errorMessage"),
						Integer.parseInt(environment.getProperty("status.note.errorCode"))));
		
		
		
		modelMapper.map(noteDto, note);
		note.setModifyDate(LocalDateTime.now());
		note = noteRepository.save(note);
		if (note == null)
			throw new NoteException(environment.getProperty("status.note.errorCode"),
					Integer.parseInt(environment.getProperty("status.note.update.errorMessage")));
		Response response = ResponseInfo.getResponse(
				Integer.parseInt(environment.getProperty("status.note.successCode")),
				environment.getProperty("status.updateNote.successMessage"));
		return response;
	}

	@Override
	public Response deleteNotePermanently(Long noteId, String token) {
		long userId = userToken.tokenVerify(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(environment.getProperty("status.user.errorMessage"),
						Integer.parseInt(environment.getProperty("status.user.errorCode"))));
//		if (!user.isVerification())
//			throw new UserException(environment.getProperty("status.user.errorMessage"),
//					Integer.parseInt(environment.getProperty("status.user.errorCode")));
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteException(environment.getProperty("status.noteId.errorMessage"),
						Integer.parseInt(environment.getProperty("status.note.errorCode"))));
		noteRepository.delete(note);
		Response response = ResponseInfo.getResponse(
				Integer.parseInt(environment.getProperty("status.note.successCode")),
				environment.getProperty("status.deleteNote.successMessage"));
		return response;
	}

	@Override
	public List<Note> getAllNote(String token) {
		long userId = userToken.tokenVerify(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(environment.getProperty("status.user.errorMessage"),
						Integer.parseInt(environment.getProperty("status.user.errorCode"))));
		List<Note> allNotes = noteRepository.findByUser(user);
		return allNotes;

	}

//pendinggggg
	@Override
	public Note getNote(String token, Long noteId) {
		long userId = userToken.tokenVerify(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(environment.getProperty("status.user.errorMessage"),
						Integer.parseInt(environment.getProperty("status.user.errorCode"))));
		Optional<Note> note = noteRepository.findById(noteId);
		if (!note.isPresent())
			throw new NoteException(environment.getProperty("status.note.getting.errorMessage"),
					Integer.parseInt(environment.getProperty("status.note.errorCode")));
		return note.get();
	}

	@Override
	public Response pinnedUnpinned(String token, Long noteId) {
		Response response;
		System.out.println("pin");
		Long userId = userToken.tokenVerify(token);
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteException(environment.getProperty("status.noteId.errorMessage"),
						Integer.parseInt(environment.getProperty("status.note.errorCode"))));
//		if (note.getUser().getId() != userId) {
//			throw new Exception("User not a match");
//		}

		System.out.println("pin34");
		if (note.isPinned()) {
			note.setPinned(false);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.false")),
					environment.getProperty("status.note.unpinnedMessage"));
		} else {
			note.setPinned(true);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.true")),
					environment.getProperty("status.note.pinnedMessage"));
		}
		note = noteRepository.save(note);
		return response;
	}

	@Override
	public Response trashedUntrashed(String token, Long noteId) {
		Response response;
		Long userId = userToken.tokenVerify(token);
		User user = userRepository.findById(userId).get();
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteException(environment.getProperty("status.noteId.errorMessage"),
						Integer.parseInt(environment.getProperty("status.note.errorCode"))));
//		if (note.getUser().getId() != userId) {
//			throw new Exception("User not a match");
//		}
		if (note.isTrash()) {
			note.setTrash(false);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.false")),
					environment.getProperty("status.note.untrashedMessage"));
		} else {
			note.setTrash(true);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.true")),
					environment.getProperty("status.note.trashedMessage"));
		}
		note = noteRepository.save(note);
		return response;
	}

	@Override
	public Response archiveUnarchive(String token, Long noteId) {
		Response response;
		// Long userId = userToken.tokenVerify(token);
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteException(environment.getProperty("status.noteId.errorMessage"),
						Integer.parseInt(environment.getProperty("status.note.errorCode"))));
		if (note.isArchive()) {
			note.setArchive(false);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.false")),
					environment.getProperty("status.note.unarchiveMessage"));
		} else {
			note.setArchive(true);
			response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.true")),
					environment.getProperty("status.note.archiveMessage"));
		}
		note = noteRepository.save(note);
		return response;
	}

	@Override
	public Response addLabelToNote(String token, long noteId, long labelId) {

		Long userId = userToken.tokenVerify(token);
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteException(environment.getProperty("status.noteId.errorMessage"),
						Integer.parseInt(environment.getProperty("status.note.errorCode"))));
		Label label = labelRepository.findById(labelId)
				.orElseThrow(() -> new NoteException(environment.getProperty("status.labeId.errorMessage"),
						Integer.parseInt(environment.getProperty("status.note.errorCode"))));
		note.getLabels().add(label);
		label.getNotes().add(note);
		// stream the list and use the set to filter it
		List<Label> labels = note.getLabels().stream().filter(e -> (e.getLableId() == labelId))
				.collect(Collectors.toList());

		System.out.println(labels);
		noteRepository.save(note);
		labelRepository.save(label);

		Response response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.true")),
				environment.getProperty("status.label.addMessage"));
		return response;
		// return null;
	}

	@Override
	public Response removeLabelFromNote(String token, long noteId, long labelId) {
		System.out.println("hello############");

		Long userId = userToken.tokenVerify(token);
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteException(environment.getProperty("status.noteId.errorMessage"),
						Integer.parseInt(environment.getProperty("status.note.errorCode"))));
		Label label = labelRepository.findById(labelId)
				.orElseThrow(() -> new NoteException(environment.getProperty("status.labeId.errorMessage"),
						Integer.parseInt(environment.getProperty("status.note.errorCode"))));
		note.getLabels().remove(label);
		// getLabel().remove(label);
		label.getNotes().remove(note);

		noteRepository.save(note);

		labelRepository.save(label);

		Response response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.false")),
				environment.getProperty("status.label.removeMessage"));
		return response;
	}

	@Override
	public Response ReminderSet(long noteId, String time, String token) throws ParseException {
		System.out.println("33333");
		Long userId = userToken.tokenVerify(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(environment.getProperty("status.user.errorMessage"),
						Integer.parseInt(environment.getProperty("status.user.errorCode"))));

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		Date date = dateFormat.parse(time);
		System.out.println(date);
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteException(environment.getProperty("status.noteId.errorMessage"),
						Integer.parseInt(environment.getProperty("status.note.errorCode"))));
		if (note.getUser().getId() == userId && date != null) {
			note.setReminder(date);
			System.out.println(note);
			noteRepository.save(note);
			Response response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.true")),
					environment.getProperty("status.reminder.successMessage"));
			return response;
		} else {

			Response response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.false")),
					environment.getProperty("status.reminder.errorMessage"));
			return response;
		}

	}

	@Override
	public Response ReminderRemove(long noteId, String token) throws ParseException {
		System.out.println("33333");
		Long userId = userToken.tokenVerify(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(environment.getProperty("status.user.errorMessage"),
						Integer.parseInt(environment.getProperty("status.user.errorCode"))));

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteException(environment.getProperty("status.noteId.errorMessage"),
						Integer.parseInt(environment.getProperty("status.note.errorCode"))));
		note.setReminder(null);
		noteRepository.save(note);
		Response response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.false")),
				environment.getProperty("status.reminder.removeMessage"));
		return response;
	}

	@Override
	public Response colorSet(long noteId, String color, String token) {
		Response response;
		Long userId = userToken.tokenVerify(token);
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new NoteException(environment.getProperty("status.noteId.errorMessage"),
						Integer.parseInt(environment.getProperty("status.note.errorCode"))));
		note.setColor(color);
		noteRepository.save(note);
		response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.note.true")),
				environment.getProperty("status.color.successMessage"));
		return response;
	}

}
