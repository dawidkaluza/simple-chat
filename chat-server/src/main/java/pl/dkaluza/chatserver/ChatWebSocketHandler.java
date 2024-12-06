package pl.dkaluza.chatserver;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

public class ChatWebSocketHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.receive()
            .doOnNext(message -> {
                System.out.println("Got the message...");
                var nickname = session.getAttributes().get("Nickname");
                System.out.println("It's from: " + nickname);
            })
            .then();
    }
}
