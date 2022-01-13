package com.zensar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.zensar.controller.OrderMessageConsumerController;
import com.zensar.dto.AddressDto;
import com.zensar.dto.BillingAddressDto;
import com.zensar.dto.ContactDto;
import com.zensar.dto.JsonOrderDto;
import com.zensar.dto.NameDto;
import com.zensar.dto.OrderTotalsDto;
import com.zensar.dto.SourceDto;
import com.zensar.dto.XmlOrderDto;
import com.zensar.service.JsonOrderConsumerService;


@WebMvcTest(OrderMessageConsumerController.class)
public class OrderMessageConsumerControllerTest {

	 @Autowired
	 MockMvc mockMvc;
	 
	 @MockBean
	 JsonOrderConsumerService jsonOrderConsumerService;
	 
	
	 JsonOrderDto JSON_ORDER_1 = new JsonOrderDto(0, "ITEM MASTER 11","ADD","WALLET","FLAT WALLET",2.3,0.5,1.3,
			 1.5,null,"Y",1,"E",null);
	 
	 XmlOrderDto XML_ORDER_1 = new XmlOrderDto(490048480,"Digital4","SAN10000137","Created","2017-08-16T15:20:29.657-04:00",
			 "POOL",110,"IN_PROCESS", 71,139, new SourceDto("MCOM","SITE","MCOM",0,1),
			 new OrderTotalsDto(41.95,0), new BillingAddressDto(new ContactDto(51238851, 
			 new NameDto( "SUPRAJA","CHIDURAL",0,1), new AddressDto("2200 Galvin Dr",
	         "2200 Galvin Dr", "2200 Galvin Dr","BUFORD","GA",30087,"USA",0),
			 "968-789-87642345","968-789-8764","741-119-3501",false,"SUPRAJA.CHIDURAL@MACYS.COM",0)),null,null);
	 
	
	 @Test
	 public void testEmptyOrderConsumeList() throws Exception {
				
		 ResponseEntity<List<JsonOrderDto>> expectedResult = new ResponseEntity<List<JsonOrderDto>>(HttpStatus.OK);
		 Mockito.when(jsonOrderConsumerService.consumeNewJsonOrder()).thenReturn(expectedResult);
			
	 }
	    
			
	 @Test
	 public void testConsumeNewJsonOrder() throws Exception {
				 
		 List<JsonOrderDto> ordersList = new ArrayList<>(Arrays.asList(JSON_ORDER_1));

		 ResponseEntity<List<JsonOrderDto>> expectedResult = new ResponseEntity<>(ordersList,HttpStatus.OK);
		 Mockito.when(jsonOrderConsumerService.consumeNewJsonOrder()).thenReturn(expectedResult);
			
//		 MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/consume/jsonorder")
//		            .contentType(MediaType.APPLICATION_JSON)
//		            .accept(MediaType.APPLICATION_JSON);
//		 MvcResult result = mockMvc.perform(mockRequest).andReturn();
//		 System.out.println("json order response : " + result.getResponse().getContentAsString());
//		 assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

//		 JSONAssert.assertEquals(ordersList.toString(), result.getResponse()
//					.getContentAsString(), true);
			
		 mockMvc.perform(MockMvcRequestBuilders
		            .get("/consume/jsonorder")
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$[0].messageName", is("ITEM MASTER 11")));
		 
	 }
	 
	 
	 @Test
	 public void testConsumeNewXmlOrder() throws Exception {
				 
		 List<XmlOrderDto> ordersList = new ArrayList<>(Arrays.asList(XML_ORDER_1));

		 ResponseEntity<List<XmlOrderDto>> expectedResult = new ResponseEntity<>(ordersList,HttpStatus.OK);
		 Mockito.when(jsonOrderConsumerService.cosumeXmlOrders()).thenReturn(expectedResult);
			
		 mockMvc.perform(MockMvcRequestBuilders
		            .get("/consume/xmlorder")
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$[0].orderTypeCode", is("Digital4")));
		 
	 }
	    
}
