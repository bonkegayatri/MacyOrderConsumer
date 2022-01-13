package com.zensar.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfig {

	static final String JSON_QUEUE = "${jsonorder.rabbitmq.queue}";
	static final String XML_QUEUE = "${xmlorder.rabbitmq.queue}";
	static final String JSON_EXCHANGE = "${jsonorder.rabbitmq.exchange}";
	static final String XML_EXCHANGE = "${xmlorder.rabbitmq.exchange}";
	static final String JSON_ROUTING_KEY = "${jsonorder.rabbitmq.routingkey}";
	static final String XML_ROUTING_KEY = "${xmlorder.rabbitmq.routingkey}";

		@Bean
	    Queue jsonQueue() {
	        return new Queue(JSON_QUEUE, true);
	    }

	    @Bean
	    Queue xmlQueue() {
	        return new Queue(XML_QUEUE, true);
	    }

	    @Bean
	    DirectExchange jsonExchange() {
	        return new DirectExchange(JSON_EXCHANGE);
	    }
	    
	    @Bean
	    DirectExchange xmlExchange() {
	        return new DirectExchange(XML_EXCHANGE);
	    }

	    @Bean
	    Binding binding1(@Qualifier("jsonExchange") DirectExchange exchange) {
	        return BindingBuilder.bind(jsonQueue()).to(exchange).with(jsonQueue().getName());
	    }

	    @Bean
	    Binding binding2(@Qualifier("xmlExchange") DirectExchange exchange) {
	        return BindingBuilder.bind(xmlQueue()).to(exchange).with(xmlQueue().getName());
	    }

	    @Bean
	    public AmqpTemplate jsonAmqpTemplate(ConnectionFactory connectionFactory) {
	        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	        rabbitTemplate.setRoutingKey(JSON_ROUTING_KEY);
	        rabbitTemplate.setDefaultReceiveQueue(jsonQueue().getName());
	        return rabbitTemplate;
	    }

	    @Bean
	    public AmqpTemplate xmlAmqpTemplate(ConnectionFactory connectionFactory) {
	        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	        rabbitTemplate.setRoutingKey(XML_ROUTING_KEY);
	        rabbitTemplate.setDefaultReceiveQueue(xmlQueue().getName());
	        return rabbitTemplate;
	    }

	    @Bean
	    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
	        return new RabbitAdmin(connectionFactory);
	    }
	    
}
