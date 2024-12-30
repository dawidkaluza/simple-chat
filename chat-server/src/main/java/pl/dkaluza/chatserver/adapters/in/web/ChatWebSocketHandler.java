package pl.dkaluza.chatserver.adapters.in.web;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
class ChatWebSocketHandler implements WebSocketHandler {
    private final ChatWebSocketFacade facade;

    public ChatWebSocketHandler(ChatWebSocketFacade facade) {
        this.facade = facade;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        var input = session.receive();
        facade.send(session, input);
        var monoInput = input.then();

        var monoOutput = session.send(facade.receive(session)).then();
        return Mono.zip(monoInput, monoOutput).then();
    }
}
