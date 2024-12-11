package pl.dkaluza.chatserver;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {
    private final ChatWebSocketEventsBroker eventsBroker;

    public ChatWebSocketHandler(ChatWebSocketEventsBroker eventsBroker) {
        this.eventsBroker = eventsBroker;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        var input = session.receive()
            .doOnNext(message -> {
                var nickname = session.getAttributes().get("Nickname");
                var incomingPayload = message.getPayloadAsText();
                var outgoingPayload = nickname + ": " + incomingPayload;
                eventsBroker.push(outgoingPayload);
            })
            .then();

        var outgoingFlux = eventsBroker.pull();
        var output = session.send(outgoingFlux.map(session::textMessage));

        return Mono.zip(input, output).then();
    }
}
