package pl.dkaluza.chatserver.adapters.in.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;

@Configuration
class ChatWebSocketConfig {
    @Bean
    public HandlerMapping handlerMapping(ChatWebSocketHandler handler) {
        var map = new HashMap<String, WebSocketHandler>();
        map.put("/chat", handler);
        return new SimpleUrlHandlerMapping(map, -1);
    }
}
