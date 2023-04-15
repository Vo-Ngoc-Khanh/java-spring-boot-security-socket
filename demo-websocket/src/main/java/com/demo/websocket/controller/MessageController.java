package com.demo.websocket.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.demo.websocket.message.Message;
import com.demo.websocket.message.OutputMessage;


@Controller
public class MessageController {
	
	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public OutputMessage send(Message message) {
		String time = new SimpleDateFormat("HH:ss").format(new Date());
		return new OutputMessage(message.getFrom(),message.getText(),time);
	}


}

