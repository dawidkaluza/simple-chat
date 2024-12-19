package pl.dkaluza.chatserver.ports.out;

import org.reactivestreams.Publisher;
import pl.dkaluza.chatserver.domain.Message;

public interface MessageRepository {
    Publisher<Message> insert(Publisher<Message> message);
}
