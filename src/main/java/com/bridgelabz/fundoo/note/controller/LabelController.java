package com.bridgelabz.fundoo.note.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.services.LabelService;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.utility.ResponseInfo;

@RestController
@PropertySource("classpath:message.properties")
@CrossOrigin(origins="http://localhost:4200")
public class LabelController {
	private static final Logger logger = LoggerFactory.getLogger(LabelController.class);
	@Autowired
	private LabelService labelService;
	@Autowired
	Environment environment;

//	@Autowired
//	Response response;

	@PostMapping("/createLabel")
	public ResponseEntity<Response> create(@RequestBody LabelDto labelDto, @RequestHeader("jwt_Token")  String token)
	{
		logger.info("Label DTO:"+labelDto);
		logger.info("Token:"+token);
		logger.trace("Create note:");
	
		Response response;
//		if(labelDto.getName().equals("") || labelDto.getName() == null)
//		 response = ResponseInfo.getResponse(-499,"label  should not be empty");
//		else
	
           response=labelService.createLabel(labelDto, token);
		return new ResponseEntity<>(response,HttpStatus.OK);


	}
	@PostMapping("/updateLabel/{labelId}")
	public ResponseEntity<Response> update(@RequestBody LabelDto labelDto,@RequestHeader("jwt_Token") String token,@PathVariable long labelId)
	{
		logger.info("Label DTO:"+labelDto);
		logger.info("Token:"+token);
		logger.trace("Update note:");
		Response response;
		response = labelService.updateLabel(labelDto, token, labelId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

//	@DeleteMapping("/deleteLabel/{lableId}")
//	public ResponseEntity<Response> deleteLablePermanently(@PathVariable(value="labelId")  long labelId, @RequestParam  String token) 
//	{
//		logger.info("Label Id:"+labelId);
//		logger.trace("Delete Label:");
//		System.out.println(labelId);
//	    System.out.println("gggggggggggggggggggggggggggggggggggg");
//		Response response = labelService.deleteLable(labelId, token);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//	

	
	@DeleteMapping("/deleteLabel/{labelId}")
	public ResponseEntity<Response> deleteLablePermanently(@RequestHeader("jwt_Token") String token,@PathVariable(value="labelId") long labelId)
	{
		
		logger.info("Token:"+token);
		logger.trace("Update note:");
		Response response = labelService.deleteLable(labelId, token);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	@GetMapping("/allLabels")
	public ResponseEntity<List<Label>> getAllLabels(@RequestHeader("jwt_Token") String token)
	{
		logger.info("Token:"+token);
		logger.info("Get all Labels:");
		List<Label> allLabels=labelService.getAllLabels(token);
		return new ResponseEntity<>(allLabels, HttpStatus.OK);

	}

}
