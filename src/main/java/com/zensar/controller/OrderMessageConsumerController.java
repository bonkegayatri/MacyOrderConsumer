package com.zensar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.dto.JsonOrderDto;
import com.zensar.dto.XmlOrderDto;
import com.zensar.service.JsonOrderConsumerService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("consume")
public class OrderMessageConsumerController {

	@Autowired
	private JsonOrderConsumerService jsonOrderConsumerService;
	
	@GetMapping(value = "/jsonorder", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "Consume Json Order")
	public ResponseEntity<List<JsonOrderDto>> consumeNewJsonOrder() {
	    return jsonOrderConsumerService.consumeNewJsonOrder();
	}
	
	@GetMapping(value = "/xmlorder", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "Consume Xml Order")
	public ResponseEntity<List<XmlOrderDto>> consumeNewXmlOrder() {
	    return jsonOrderConsumerService.cosumeXmlOrders();
	}
	
}
