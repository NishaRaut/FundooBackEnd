package com.bridgelabz.fundoo.user.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.User;

public interface UserServices {
	Response register(UserDTO userDTO) throws Exception;

	ResponseToken login(LoginDTO loginuser, HttpServletResponse res) throws Exception;

	String validateEmailId(String token) throws Exception;

	Response forgotPassword(String email) throws Exception;

	Response resetPassword(String token, String password) throws Exception;

	Response imageUpload(String image, String token);

	String getImage(long id);
//
//	List<User> getAll();
//
//	List<User> getById(String token);
//
//	public Response getUserData(String email);
//	public List<User> getByEmail(String email);

	//User findByEmail(String email);
}
