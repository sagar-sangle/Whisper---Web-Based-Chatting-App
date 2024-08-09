package com.project.randomly.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker//  used to enable our WebSocket server.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    // register a websocket endpoint that the clients will
    // use to connect to our websocket server.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {//STOMP in the method name. These methods come from Spring frameworks STOMP implementation. STOMP stands for Simple Text Oriented Messaging Protocol. It is a messaging protocol that defines the format and rules for data exchange.


        registry.addEndpoint("/ws").withSockJS();//This method is part of the WebSocket configuration and is used to
        // register one or more STOMP (Simple Text Oriented Messaging Protocol) endpoints that clients can connect to.


        //withSockJS() : the endpoint configuration. SockJS is
        // used to enable fallback options for browsers that don’t support websocket.
    }


    //In the below method, we’re configuring a message broker that will be used to route messages from one client to another.
    //The first line defines that the messages whose destination starts with “/app” should be routed to message-handling methods (we’ll define these methods shortly).
    //And, the second line defines that the messages whose destination starts with “/topic” should be routed to the message broker. Message broker broadcasts messages to all the connected clients who are subscribed to a particular topic.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }
}
