package com.bridgelabz.fundoo.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.user.services.MailService;

@Component
public class RabbitMQServiceImpl implements RabbitMQService {
	@Autowired
	private AmqpTemplate amqpTemplate;
	@Value("${spring.rabbitmq.template.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.template.routing-key}")
	private String userRoutingKey;
	@Autowired
	private MailService mailService;

	private static final Logger logger = LoggerFactory.getLogger(RabbitMQServiceImpl.class);
	
	@Override
	public void publishUserMail(SimpleMailMessage mail) {
		logger.info("published message = " + mail);
		logger.info("exchange = "+exchange);
		logger.info("routingKey = "+userRoutingKey);
		amqpTemplate.convertAndSend(exchange, userRoutingKey, mail);
	}

	@Override
	@RabbitListener(queues="${spring.rabbitmq.template.default-receive-queue}")
	public void recieveUserMail(SimpleMailMessage mail) {
		logger.info("consumed message = "+ mail.toString());
		mailService.sendEmail(mail);
	}
}
