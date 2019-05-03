package com.bridgelabz.fundoo.user.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class MailService {
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	Environment environment;
	
	public void sendEmail(SimpleMailMessage mail) {
		try {
		javaMailSender.send(mail);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}	
	
/*	public void sendEmail(String to, String subject, String message) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(to);
		mail.setFrom(environment.getProperty("spring.mail.username"));
		mail.setSubject(subject);
		mail.setText(message);
		try {
		javaMailSender.send(mail);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}*/	
}
