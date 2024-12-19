package pl.dkaluza.chatserver.adapters.out.eventpublisher;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import pl.dkaluza.chatserver.domain.Message;
import pl.dkaluza.chatserver.ports.out.MessageSender;

@Component
class MessageSenderAdapter implements MessageSender {

    @Override
    public void send(Publisher<Message> message) {
        // TODO send message to all opened connections
    }
}
