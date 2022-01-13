package com.zensar.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.zensar.dto.JsonOrderDto;
import com.zensar.dto.XmlOrderDto;

public interface JsonOrderConsumerService {

	public ResponseEntity<List<JsonOrderDto>> consumeNewJsonOrder();
	public ResponseEntity<List<XmlOrderDto>> cosumeXmlOrders();

}
