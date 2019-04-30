package com.bridgelabz.fundoo.user.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.TokenException;
import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.utility.ResponseInfo;
import com.bridgelabz.fundoo.utility.UserToken;

@Service
@PropertySource("classpath:message.properties")
public class UserServiceImpl implements UserServices {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Environment environment;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Response response;

	@Autowired
	private MailService mailService;
	@Autowired
	private UserToken userToken;

	@Override
	public Response register(UserDTO userDTO) {
		Optional<User> useravailable = userRepository.findByEmail(userDTO.getEmail());
		//To check user is available or not if found it will show duplicate user found.
		if (useravailable.isPresent()) 
			throw new UserException(environment.getProperty("status.register.duplicateEmailError"),
					Integer.parseInt(environment.getProperty("status.register.errorCode")));

		// map the UserDTO to User
		User user = modelMapper.map(userDTO, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		LocalDate today = LocalDate.now();
		user.setRegisteredDate(today);
		user.setModifiedDate(today);
		//user.setVerification(false);
		user = userRepository.save(user);
		try {
			user = userRepository.save(user);
			}
	catch(Exception e)
		{
			throw new UserException(environment.getProperty("status.saveError"),
					Integer.parseInt(environment.getProperty("status.dataSaving.errorCode")));
		}
		String userActivationLink = "http://localhost:8080/userActivation/";
		userActivationLink = userActivationLink + userToken.generateToken(user.getId());
		mailService.sendEmail(user.getEmail(),"user activation link",userActivationLink);
		response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("status.register.success"));
		return response;
	
	}

	@Override
	public ResponseToken login(LoginDTO loginuser,HttpServletResponse httpresponse) throws Exception 
	{	
		Optional<User> is_user_available=userRepository.findByEmail(loginuser.getEmail());
		if(is_user_available.isPresent() && passwordEncoder.matches(loginuser.getPassword(),is_user_available.get().getPassword()) && is_user_available.get().isVerification()) 
		{ 	
			String generatedToken =userToken.generateToken(is_user_available.get().getId());
			httpresponse.addHeader("JWT-Token",generatedToken);
			System.out.println(generatedToken);
			ResponseToken response =ResponseInfo.tokenStatusInfo(200, "login success", generatedToken );
			return response; 
		} 

		else 
		{ 
			throw new Exception("Email and Password is not found"); 
		}

	}

	@Override
	public String validateEmailId(String token) throws Exception {
		Long id = userToken.tokenVerify(token);
		User user = userRepository.findById(id).orElseThrow(() -> new Exception("User not found")); 
		user.setVerification(true);		
		userRepository.save(user);
		return "Successfully verified";
	}

	@Override
	public Response forgotPassword(String email) throws Exception {
		Optional<User> userAvailable = userRepository.findByEmail(email);
		if (!userAvailable.isPresent())
		{
			throw new UserException(environment.getProperty("status.forgotPassword.invalidEmail"), Integer.parseInt(environment.getProperty("status.forgotPassword.errorCode")));
		}
		String passwordResetLink = "http://localhost:4200/reset/";
		passwordResetLink = passwordResetLink + userToken.generateToken(userAvailable.get().getId());
		mailService.sendEmail(userAvailable.get().getEmail(),"Password Reset Link",passwordResetLink);
		Response response = ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.success.code")),
				environment.getProperty("status.forgotPassword.success"));
		return response;
	}

	@Override
	public Response resetPassword(String newPassword, String token) throws Exception {
		Long id = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent())
			throw new UserException(environment.getProperty("status.user.invalidUser"), Integer.parseInt(environment.getProperty("status.user.errorCode")));
		
		user.get().setPassword(passwordEncoder.encode(newPassword));
		LocalDate today = LocalDate.now();
		user.get().setModifiedDate(today);
		if(userRepository.save(user.get()) == null)
		   throw new UserException(environment.getProperty("status.saveError"),Integer.parseInt(environment.getProperty("status.dataSaving.errorCode")));
		return ResponseInfo.getResponse(Integer.parseInt(environment.getProperty("status.success.code")),environment.getProperty("status.resetPassword.success"));
	}
  

	public Response imageUpload(String token,String image)
	{
		
		long userID = userToken.tokenVerify(token);
		
		   User user=userRepository.findById(userID)
				   .orElseThrow(() -> new TokenException("User is not valid.........",400));
		   
	 user.setImage(image);
	 userRepository.save(user);
	Response response=ResponseInfo.getResponse(102,environment.getProperty("user.upload.message"));
		
		return  response;
	}
	
}