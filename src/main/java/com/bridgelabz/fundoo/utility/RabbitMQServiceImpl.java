package com.bridgelabz.fundoo.utility;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.elasticserach.ElasticsearchImlementation;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.user.services.MailService;
@Service
@Component
public class RabbitMQServiceImpl implements RabbitMQService {
	@Autowired
	private AmqpTemplate amqpTemplate;
	@Value("${spring.rabbitmq.template.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.template.routing-key}")
	private String userRoutingKey;
	@Value("${spring.rabbitmq.template.routing-key2}")
	private String noteRoutingKey;
	@Autowired
	private MailService mailService;
	@Autowired
	private ElasticsearchImlementation elasticSearch;

	private static final Logger logger = LoggerFactory.getLogger(RabbitMQServiceImpl.class);
	
	@Override
	public void publishUserMail(SimpleMailMessage mail) {
		logger.info("published message = " + mail);
		logger.info("exchange = "+exchange);
		logger.info("routingKey = "+userRoutingKey);
		amqpTemplate.convertAndSend(exchange, userRoutingKey, mail);
	}

	 public void sendNote(NoteContainer noteContainer)
	 {
		 amqpTemplate.convertAndSend(exchange,noteRoutingKey, noteContainer);
//		 System.out.println("Is listener returned ::: "+amqpTemplate.isReturnListener());
//	     System.out.println(new Date());
	 }


	@Override
	@RabbitListener(queues="${spring.rabbitmq.template.default-receive-queue}")
	public void recieveUserMail(SimpleMailMessage mail) {
		logger.info("consumed message = "+ mail.toString());
		mailService.sendEmail(mail);
	}

	@Override
	@RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue2}")
	public void operation(NoteContainer notecontainer) {
		System.out.println("hiiiiiii");
		
		
		Note note=notecontainer.getNote();
		switch(notecontainer.getNoteoperation())
		{
		case CREATE : try {

			elasticSearch.create(note);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		break;

		case UPDATE :elasticSearch.updateNote(note);
		break;

		case DELETE :elasticSearch.deleteNote(note.getId());
		break;
		}	
	}
	
	
}
