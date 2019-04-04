package com.bridgelabz.fundoo.user.services;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;

public interface UserServices {
	public Response register(UserDTO userDTO) throws Exception;
	public ResponseToken login(LoginDTO loginuser,HttpServletResponse res) throws Exception;
	public String validateEmailId(String token) throws Exception; 
	public Response forgotPassword(String email) throws Exception;
	public Response resetPassword(String token, String password) throws Exception;
	
}
