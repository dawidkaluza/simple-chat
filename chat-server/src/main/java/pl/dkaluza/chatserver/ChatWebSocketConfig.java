package pl.dkaluza.chatserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;

@Configuration
public class ChatWebSocketConfig {
    @Bean
    public HandlerMapping handlerMapping(ChatWebSocketHandler handler) {
        var map = new HashMap<String, WebSocketHandler>();
        map.put("/chat", handler);
        return new SimpleUrlHandlerMapping(map, -1);
    }

    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter(new ChatWebSocketService());
    }
}
