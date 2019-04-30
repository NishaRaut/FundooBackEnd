package com.bridgelabz.fundoo.user.services;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;

public interface UserServices {
 Response register(UserDTO userDTO) throws Exception;
 ResponseToken login(LoginDTO loginuser,HttpServletResponse res) throws Exception;
 String validateEmailId(String token) throws Exception; 
 Response forgotPassword(String email) throws Exception;
 Response resetPassword(String token, String password) throws Exception;
 Response imageUpload(String image,String token);
}
