package pl.dkaluza.chatserver.ports.out;

import pl.dkaluza.chatserver.domain.Message;
import reactor.core.publisher.Flux;

public interface MessageRepository {
    Flux<Message> insert(Flux<Message> message);
}
