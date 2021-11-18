package com.web.socket.websocket.controller;

import com.web.socket.websocket.bean.MessageBean;
import com.web.socket.websocket.bean.TextMessageDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketTextController {
	
	@Autowired
    	SimpMessagingTemplate template;
	
	@PostMapping("/send/{name}")
	public ResponseEntity<Void> sendMessage(@RequestBody TextMessageDTO textMessageDTO,@PathVariable String name) {
		template.convertAndSend("/topic/message/"+name, textMessageDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@MessageMapping("/sendMessage")
	public void receiveMessage(@Payload TextMessageDTO textMessageDTO) {
		// receive message from client
	}
	
	
	@SendTo("/topic/message/{name}")
	public TextMessageDTO broadcastMessage(@Payload TextMessageDTO textMessageDTO,@DestinationVariable String name) {
		return textMessageDTO;
	}
}