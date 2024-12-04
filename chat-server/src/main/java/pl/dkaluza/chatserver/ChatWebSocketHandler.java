package pl.dkaluza.chatserver;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

public class ChatWebSocketHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        var output = session.receive()
            .map(value -> session.textMessage("Echo: " + value));

        return session.send(output);
    }
}
