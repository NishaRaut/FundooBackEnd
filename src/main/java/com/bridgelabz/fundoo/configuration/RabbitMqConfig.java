package com.bridgelabz.fundoo.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
	@Value("${spring.rabbitmq.template.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.template.default-receive-queue}")
	private String userQueue;
	@Value("${spring.rabbitmq.template.routing-key}")
	private String userRoutingKey;
	@Value("${spring.rabbitmq.template.default-receive-queue2}")
	private String noteQueue;
	@Value("${spring.rabbitmq.template.routing-key2}")
	private String noteRoutingKey;
	

	@Bean
	Exchange exchage() {
		return new DirectExchange(exchange);
	}

	@Bean
	Queue userQueue() {
		return new Queue(userQueue, true);
	}

	@Bean
	Binding userQueueBinding(Queue userQueue, DirectExchange exchange) {
		return BindingBuilder.bind(userQueue).to(exchange).with(userRoutingKey);
	}
	@Bean
	Queue noteQueue() {
		return new Queue(noteQueue, true);
	}

	@Bean
	Binding noteQueueBinding(Queue noteQueue, DirectExchange exchange) {
		return BindingBuilder.bind(noteQueue).to(exchange).with(noteRoutingKey);
	}
}
