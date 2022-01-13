package com.zensar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.zensar.dto.JsonOrderDto;
import com.zensar.dto.XmlOrderDto;
import com.zensar.entity.JsonOrderEntity;
import com.zensar.entity.XmlOrderEntity;
import com.zensar.repo.JsonOrderMessageConsumerRepo;
import com.zensar.repo.XmlOrderMessageConsumerRepo;

@Service
public class JsonOrderConsumerServiceImpl implements JsonOrderConsumerService {

	@Autowired
	RabbitAdmin rabbitAdmin;
	
	@Autowired
	AmqpTemplate xmlAmqpTemplate;
	
	@Autowired
	AmqpTemplate jsonAmqpTemplate;
	
	@Autowired(required = true)
    private MappingJackson2XmlHttpMessageConverter xmlConverter;

	static final String JSON_QUEUE = "jsonorder.queue";
	static final String XML_QUEUE = "xmlorder.queue";
	
	@Autowired
	JsonOrderMessageConsumerRepo jsonOrderMessageConsumerRepo;
	
	@Autowired
	XmlOrderMessageConsumerRepo xmlOrderMessageConsumerRepo;
	
	@Lazy
	@Autowired
	ModelMapper modelMapper;

	@Bean
	public ModelMapper modelMapper() {
	 return new ModelMapper();
	}
	
	@Bean
	public MappingJackson2XmlHttpMessageConverter xmlConverter() {
	 return new MappingJackson2XmlHttpMessageConverter();
	}
	

	@Override
	public ResponseEntity<List<JsonOrderDto>> consumeNewJsonOrder() {
		List<JsonOrderDto> listJsonMsg = new ArrayList<JsonOrderDto>();
		Properties properties = rabbitAdmin.getQueueProperties(JSON_QUEUE);
		System.out.println("properties :" + properties);

		int reqCount = (Integer)(properties != null ? properties.get(RabbitAdmin.QUEUE_MESSAGE_COUNT) : 0);
		System.out.println("reqCount :" + reqCount);
		for (int i = 0; i < reqCount; i++) {
			try {
				JsonOrderDto jsonOrderDto = new JsonMapper().readValue(new String((byte[]) jsonAmqpTemplate.receiveAndConvert(JSON_QUEUE)), JsonOrderDto.class);
				
				JsonOrderEntity jsonOrderEntity = getOrderEntityFromDto(jsonOrderDto);
		        jsonOrderEntity = jsonOrderMessageConsumerRepo.save(jsonOrderEntity);

			    listJsonMsg.add(jsonOrderDto);
			    
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("listXmlMsg :" + listJsonMsg);
		return new ResponseEntity<List<JsonOrderDto>>(listJsonMsg,HttpStatus.OK);
		
	}
	
	@Override
	public ResponseEntity<List<XmlOrderDto>> cosumeXmlOrders() {
		List<XmlOrderDto> listXmlMsg = new ArrayList<XmlOrderDto>();
		Properties properties = rabbitAdmin.getQueueProperties(XML_QUEUE);
		System.out.println("properties :" + properties);

		int reqCount = (Integer)(properties != null ? properties.get(RabbitAdmin.QUEUE_MESSAGE_COUNT) : 0);
		System.out.println("reqCount :" + reqCount);
		for (int i = 0; i < reqCount; i++) {
			try {
//				System.out.println("XML: " + new XmlMapper().readValue(new String((byte[]) xmlAmqpTemplate.receiveAndConvert(XML_QUEUE)), XmlOrderDto.class));
				XmlOrderDto xmlPojo = new XmlMapper().readValue(new String((byte[]) xmlAmqpTemplate.receiveAndConvert(XML_QUEUE)), XmlOrderDto.class);
				
				XmlOrderEntity xmlOrderEntity = getXmlOrderEntityFromDto(xmlPojo);
			    xmlOrderEntity = xmlOrderMessageConsumerRepo.save(xmlOrderEntity);
			    listXmlMsg.add(xmlPojo);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("listXmlMsg :" + listXmlMsg);
		return new ResponseEntity<List<XmlOrderDto>>(listXmlMsg,HttpStatus.OK);
		
	}
	

	private XmlOrderEntity getXmlOrderEntityFromDto(XmlOrderDto xmlOrderDto) {
		XmlOrderEntity xmlOrderEntity = this.modelMapper.map(xmlOrderDto, XmlOrderEntity.class);
		return xmlOrderEntity;
	}
	
	
	private JsonOrderEntity getOrderEntityFromDto(JsonOrderDto jsonOrderDto) {
		JsonOrderEntity jsonOrderEntity = this.modelMapper.map(jsonOrderDto, JsonOrderEntity.class);
		return jsonOrderEntity;
	}
    

	
}
