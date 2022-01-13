package com.zensar.controller;

import javax.persistence.EntityManager;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.zensar.dto.JsonOrderDto;
import com.zensar.dto.XmlOrderDto;
import com.zensar.entity.JsonOrderEntity;
import com.zensar.entity.XmlOrderEntity;
import com.zensar.repo.JsonOrderMessageConsumerRepo;
import com.zensar.repo.XmlOrderMessageConsumerRepo;

@Service
public class OrderListener {

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	JsonOrderMessageConsumerRepo jsonOrderMessageConsumerRepo;
	
	@Autowired
	XmlOrderMessageConsumerRepo xmlOrderMessageConsumerRepo;
	
//	@Lazy
//	@Autowired
//	ModelMapper modelMapper;

//	@Bean
//	public ModelMapper modelMapper() {
//	 return new ModelMapper();
//	}
	
//	private static final Logger log = LoggerFactory.getLogger(OrderListener.class);
//	static final String queueName = "${jsonorder.rabbitmq.queue}";
//	static final String xmlqueueName = "${xmlorder.rabbitmq.queue}";

//    @RabbitListener(queues = queueName)
//    public void receiveMessage(JsonOrderDto jsonOrderDto) {
//        log.info("Received message and deserialized to 'JsonOrder': {}", jsonOrderDto.toString());
//        
//        JsonOrderEntity jsonOrderEntity = getOrderEntityFromDto(jsonOrderDto);
//        jsonOrderEntity = jsonOrderMessageConsumerRepo.save(jsonOrderEntity);
//
//    }
//    
//    @RabbitListener(queues = xmlqueueName)
//    public void receiveXmlMessage(XmlOrderDto xmlOrderDto) {
//        log.info("Received xml message and deserialized to 'XmlOrderDto': {}", xmlOrderDto.toString());
//        
//        XmlOrderEntity xmlOrderEntity = getXmlOrderEntityFromDto(xmlOrderDto);
//        xmlOrderEntity = xmlOrderMessageConsumerRepo.save(xmlOrderEntity);
//
//    }

//	private XmlOrderEntity getXmlOrderEntityFromDto(XmlOrderDto xmlOrderDto) {
//		XmlOrderEntity xmlOrderEntity = this.modelMapper.map(xmlOrderDto, XmlOrderEntity.class);
//		return xmlOrderEntity;
//	}
//
//	private JsonOrderEntity getOrderEntityFromDto(JsonOrderDto jsonOrderDto) {
//		JsonOrderEntity jsonOrderEntity = this.modelMapper.map(jsonOrderDto, JsonOrderEntity.class);
//		return jsonOrderEntity;
//	}
    


		
			    
}
