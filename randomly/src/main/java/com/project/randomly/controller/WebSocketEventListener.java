package com.project.randomly.controller;

import com.project.randomly.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

//We’ll use event listeners to listen for socket connect and disconnect events
// so that we can log these events and also broadcast them when a user joins or leaves
// the chat room -
@Component
public class WebSocketEventListener {

    //This logger is a tool that helps record messages and events in the
    // WebSocketEventListener class, allowing developers to monitor, debug,
    // and understand the behavior of this specific component within the application.

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);

    //The purpose of this below  interface is to provide operations for sending messages
    // to WebSocket clients.
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;


    //This method is annotated with @EventListener and is designed to handle events when a new WebSocket session is connected.
    //The SessionConnectedEvent parameter indicates that this method will be called when a new WebSocket session is established.
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        LOGGER.info("Received a new web socket connection");
    }

   @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());//StompHeaderAccessor is a utility class provided by Spring for working with headers in STOMP (Simple Text Oriented Messaging Protocol) messages. It allows you to access and manipulate STOMP headers, which can include information about the session, subscription, and more.
        //The StompHeaderAccessor.wrap(event.getMessage()) wraps the message from the disconnect event, providing convenient methods to access STOMP-related headers.
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            LOGGER.info("User Disconnected : " + username);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }

}
