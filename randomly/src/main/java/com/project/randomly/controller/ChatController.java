package com.project.randomly.controller;

import com.project.randomly.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    //We’ll define the message handling methods in our controller.
    // These methods will be responsible for receiving messages from one client and then broadcasting it to others.

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){//The @Payload annotation is used to annotate a method parameter, indicating that the parameter should be bound to the payload of a message received by a message-handling method.

        // Message Handling Methods:
       // Typically, the @Payload annotation is used in methods annotated with @MessageMapping. These methods are responsible for handling messages sent to specific destinations in a messaging system.

        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor accessor){

        // Add username in web socket session
        accessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }





    //If you recall from the websocket configuration, all the messages sent from clients
    // with a destination starting with /app will be routed to these message handling
    // methods annotated with @MessageMapping.

    //For example, a message with destination /app/chat.sendMessage will be routed to the
    // sendMessage() method, and a message with destination /app/chat.addUser will be routed
    // to the addUser() method.

}

