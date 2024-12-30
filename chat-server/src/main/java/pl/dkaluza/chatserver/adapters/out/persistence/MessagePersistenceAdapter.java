package pl.dkaluza.chatserver.adapters.out.persistence;

import org.springframework.stereotype.Component;
import pl.dkaluza.chatserver.domain.Message;
import pl.dkaluza.chatserver.ports.out.MessageRepository;
import reactor.core.publisher.Flux;

@Component
class MessagePersistenceAdapter implements MessageRepository {
    private final MessageDocumentRepository messageRepository;

    public MessagePersistenceAdapter(MessageDocumentRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Flux<Message> insert(Flux<Message> message) {
        var savedMessage = messageRepository.insert(message.map(this::toDocument));
        return savedMessage.map(this::toDomain);
    }

    private MessageDocument toDocument(Message message) {
        return new MessageDocument(message.sender(), message.message(), message.sentAt());
    }

    private Message toDomain(MessageDocument messageDocument) {
        return Message.of(messageDocument.sender(), messageDocument.message(), messageDocument.timestamp());
    }
}
