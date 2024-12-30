package pl.dkaluza.chatserver.domain;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class MessageBroker {
    private final Flux<Message> messages;

    public MessageBroker() {
        messages = Flux.never();
    }

    public void send(Flux<Message> messages) {
        this.messages.mergeWith(messages);
    }

    public Flux<Message> receive() {
        return messages;
    }
}
