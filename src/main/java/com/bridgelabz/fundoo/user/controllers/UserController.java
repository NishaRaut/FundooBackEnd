package com.bridgelabz.fundoo.user.controllers;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.exception.UploadFileNotFoundException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;

import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.ResetDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.services.UserServices;
import com.bridgelabz.fundoo.utility.UserToken;


@RestController 
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*",exposedHeaders={"Authorization"})
public class UserController {
	
	static final Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserServices userServices;
	@Autowired
 	UserToken userToken ;
//	String pathlocation= "/home/admin1/Music";
	private final Path pathlocation=Paths.get("/home/admin1/Music");

	@PostMapping("/register")
	public ResponseEntity<Response> userRegister(@Valid @RequestBody UserDTO userDTO) throws Exception {
		
		logger.info("userDTO:"+userDTO);
		logger.trace("User Registration");
		System.out.println("Hello"+userDTO);
		Response response = userServices.register(userDTO);
		return new ResponseEntity<Response>(response , HttpStatus.OK);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<ResponseToken> login(@Valid @RequestBody LoginDTO loginDTO, 
			HttpServletResponse httpresponse) throws Exception {
		
		logger.info("loginDTO:" + loginDTO);
		logger.trace("Login");
		System.out.println(loginDTO.getEmail());
		System.out.println(loginDTO.getPassword());
		ResponseToken response = userServices.login(loginDTO, httpresponse);
		return new ResponseEntity<ResponseToken>(response, HttpStatus.OK);
	}
	
	
	
    @GetMapping("userActivation/{token}")
	public String emailValidation(@PathVariable String token) throws Exception
	{
    	System.out.println("in verify");
		logger.info("Token:"+token);
		logger.trace("User Verification");
		String result = userServices.validateEmailId(token);
		return result;
	}
	
	@PostMapping("/forgetPassword")
	public ResponseEntity<Response> forgetPassword(@RequestParam String email) throws Exception
	{
	/*	logger.info("email:"+loginDTO.getEmail());
		logger.trace("Forget Password");*/
		Response response = userServices.forgotPassword(email);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	@PutMapping("resetpassword/{token}")
	public ResponseEntity<Response> resetPassword(@RequestBody ResetDTO password,  @PathVariable String token) throws Exception{
		logger.info("user password : " + password.getPassword());
		logger.trace("reset user password");

		Response response = userServices.resetPassword(password.getPassword(), token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
}

	@RequestMapping(value = "user/profileupload", method = RequestMethod.PUT,consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Response> imageUploads(@RequestHeader(value="jwt_token") String token,@RequestParam("File") MultipartFile file) throws IOException
	{
		logger.info("uploading..");
		UUID uuid = UUID.randomUUID();
		
		System.out.println(uuid.toString());
		String uuidString = uuid.toString();
	
		//File convertFile=new File(this.pathlocation+"/"+file.getOriginalFilename());
		//System.out.println(convertFile);
		String test = this.pathlocation.resolve(uuidString).toString();
		 Files.copy(file.getInputStream(), this.pathlocation.resolve(uuidString),
                 StandardCopyOption.REPLACE_EXISTING);
		Response response=userServices.imageUpload(token,uuid.toString());
		
		
		return  new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	 @GetMapping("/user/getProfile/{token}")
	    public Resource getImageAll(@PathVariable("token") String token) {
			System.out.println("@@@@@@@@@@@");
			System.out.println("TTTTTTTTT:"+token);
			

	    	long userId = userToken.tokenVerify(token);
	    	System.out.println("YUUUUUUUUUUUU:"+userId);
	    	String filename=userServices.getImage(userId);
	    	
	    	System.out.println("file name:"+filename);
	    	  Path file = this.pathlocation.resolve(filename);
	    	System.out.println(file);
	        try {
	          
	            Resource resource = new UrlResource(file.toUri());
	            if (resource.exists() || resource.isReadable()) {
	                return resource;
	            } else {
	                throw new UploadFileNotFoundException(
	                        "Could not read file: " + filename);

	            }
	        } catch (MalformedURLException e) {
	            throw new UploadFileNotFoundException("Could not read file: " + filename, e);
	        }
	    }


}

