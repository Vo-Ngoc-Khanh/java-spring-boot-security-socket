package com.demo.websocket.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Message {
	
    private String from;
    
    private String text;     
}

