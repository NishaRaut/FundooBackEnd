package com.bridgelabz.fundoo.note.services;


import java.time.LocalDateTime;
import java.util.List;


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
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.repository.LabelRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.utility.ResponseInfo;
import com.bridgelabz.fundoo.utility.UserToken;
@Service
@PropertySource("classpath:message.properties")
public class LabelServiceImplementation implements LabelService{
	
	public static final Logger logger= LoggerFactory.getLogger(LabelServiceImplementation.class); 
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private UserToken userToken;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private Environment environment;
//	@Autowired
//	Response response;
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public Response createLabel(LabelDto labelDto, String token)
	{
		System.out.println("fgfgfd");
		logger.info("Token"+token);
		logger.trace("Create label:");
		Long userId =userToken.tokenVerify(token);
       System.out.println("fgfgfd");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(environment.getProperty("status.user.errorMessage"),
				Integer.parseInt(environment.getProperty("status.user.errorCode"))));
    	
		Label label = modelMapper.map(labelDto,Label.class);
		 System.out.println("fgsdsdsfgfd");
		label.setCreateStamp(LocalDateTime.now());
	    label.setModifiedStamp(LocalDateTime.now());
		label.setUser(user);
		label = labelRepository.save(label);
		Response response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.label.successCode")),
			environment.getProperty("status.createLabel.successMessage"));
		return response;
	}


	@Override
	public Response updateLabel(LabelDto labelDto, String token, long labelId)  {
		logger.info("LabelDto:"+labelDto);
		logger.info("Label Id:"+labelId);
		logger.trace("Update note:");
		Long userId =userToken.tokenVerify(token);
	User user = userRepository.findById(userId).orElseThrow(() -> new UserException(environment.getProperty("status.user.errorMessage"),
			Integer.parseInt(environment.getProperty("status.user.errorCode"))));
	
	 if(!user.isVerification())
		 throw new UserException(environment.getProperty("status.login.unVerifiedUser"),Integer.parseInt(environment.getProperty("status.user.errorCode")));
	 Label label= labelRepository.findByLableIdAndUser(labelId, user).orElseThrow(() -> new NoteException(environment.getProperty("status.labeId.errorMessage"),
				Integer.parseInt(environment.getProperty("status.label.errorCode"))));
	 modelMapper.map(labelDto,label);
	 label.setModifiedStamp(LocalDateTime.now());
		label= labelRepository.save(label);
		if(label==null)
		throw new NoteException(environment.getProperty("status.updateLabel.failedMessage"), Integer.parseInt(environment.getProperty("status.label.errorCode")));
		Response response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.label.successCode")),
				environment.getProperty("status.updateLabel.successMessage"));
		return response;
	}


	@Override
	public Response deleteLable(Long labelId , String token) {
		
		long userId=userToken.tokenVerify(token);
		User user = userRepository.findById(userId).orElseThrow(() -> new UserException(environment.getProperty("status.user.errorMessage"),
				Integer.parseInt(environment.getProperty("status.user.errorCode"))));
		
		
	   Label label=labelRepository.findById(labelId).orElseThrow(() -> new NoteException(environment.getProperty("status.label.missing"), Integer.parseInt(environment.getProperty("status.label.errorCode")))); 
	   labelRepository.delete(label);

	    Response response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.label.successCode")),
		environment.getProperty("status.deleteLabel.successMessage"));
		return response;
	   }



	@Override
	public List<Label> getAllLabels(String token) {
		long userId=userToken.tokenVerify(token);
		User user = userRepository.findById(userId).orElseThrow(() -> new UserException(environment.getProperty("status.user.errorMessage"),
				Integer.parseInt(environment.getProperty("status.user.errorCode"))));
		List<Label> allLabels =labelRepository.findAllByUser(user);
		return allLabels;
	}

	
	
}
