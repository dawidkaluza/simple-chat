package pl.dkaluza.chatserver.ports.in;

import org.reactivestreams.Publisher;
import pl.dkaluza.chatserver.domain.Message;

public interface SendMessageUseCase {
    void sendMessage(Publisher<Message> message);
}
