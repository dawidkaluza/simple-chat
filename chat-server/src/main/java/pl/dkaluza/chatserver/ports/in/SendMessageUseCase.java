package pl.dkaluza.chatserver.ports.in;

import pl.dkaluza.chatserver.domain.Message;
import reactor.core.publisher.Flux;

public interface SendMessageUseCase {
    void sendMessage(Flux<Message> message);
}
