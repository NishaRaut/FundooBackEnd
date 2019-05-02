package com.bridgelabz.fundoo.utility;

import org.springframework.mail.SimpleMailMessage;

public interface RabbitMQService {
	void publishUserMail(SimpleMailMessage mail);
	void recieveUserMail(SimpleMailMessage mail);
}
