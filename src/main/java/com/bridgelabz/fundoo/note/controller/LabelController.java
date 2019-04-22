package com.bridgelabz.fundoo.note.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.services.LabelService;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.utility.ResponseInfo;

@RestController
@PropertySource("classpath:message.properties")
public class LabelController {
	private static final Logger logger = LoggerFactory.getLogger(LabelController.class);
	@Autowired
	private LabelService labelService;
	@Autowired
	Environment environment;

//	@Autowired
//	Response response;

	@PostMapping("/createLabel")
	public ResponseEntity<Response> create(@RequestBody LabelDto labelDto, @RequestHeader String token) throws Exception
	{
		logger.info("Label DTO:"+labelDto);
		logger.info("Token:"+token);
		logger.trace("Create note:");
		Response response;
		if(labelDto.getName().equals("") || labelDto.getName() == null)
		 response = ResponseInfo.getResponse(-499,"label  should not be empty");
		else
           response=labelService.createLabel(labelDto, token);
		return new ResponseEntity<>(response,HttpStatus.OK);


	}
	@PostMapping("/updateLabel/{labelId}")
	public ResponseEntity<Response> update(@RequestBody LabelDto labelDto,@RequestHeader String token,@PathVariable long labelId)throws Exception
	{
		logger.info("Label DTO:"+labelDto);
		logger.info("Token:"+token);
		logger.trace("Update note:");
		Response response;
		if(labelDto.getName().equals("") || labelDto.getName() == null)
			 response = ResponseInfo.getResponse(-499,"label name should not be empty");
		else
			response = labelService.updateLabel(labelDto, token, labelId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@PutMapping("/deleteLabel")
	public ResponseEntity<Response> deleteLablePermanently(@RequestBody Long labelId, @RequestHeader String token) throws Exception
	{
		logger.info("Label Id:"+labelId);
		logger.trace("Delete Label:");
		Response response = labelService.deleteLable(labelId, token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@GetMapping("/allLabels")
	public ResponseEntity<List<Label>> getAllLabels(@RequestHeader String token) throws Exception
	{
		logger.info("Token:"+token);
		logger.info("Get all Labels:");
		List<Label> allLabels=labelService.getAllLabels(token);
		return new ResponseEntity<>(allLabels, HttpStatus.OK);

	}

}
