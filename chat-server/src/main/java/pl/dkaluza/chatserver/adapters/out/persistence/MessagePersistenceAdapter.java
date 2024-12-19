package pl.dkaluza.chatserver.adapters.out.persistence;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import pl.dkaluza.chatserver.domain.Message;
import pl.dkaluza.chatserver.ports.out.MessageRepository;
import reactor.core.publisher.Flux;

import java.time.Clock;
import java.time.Instant;

@Component
class MessagePersistenceAdapter implements MessageRepository {
    private final MessageDocumentRepository messageRepository;

    public MessagePersistenceAdapter(MessageDocumentRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Publisher<Message> insert(Publisher<Message> input) {
        return Flux.from(input)
            .map(message -> new MessageDocument(message.getSender(), message.getMessage(), Instant.now(Clock.systemUTC())))
            .flatMap(messageRepository::save)
            .map(document -> Message.of(document.sender(), document.message()));
    }
}
