package pl.dkaluza.chatserver.domain;

import pl.dkaluza.chatserver.ports.in.SendMessageUseCase;
import pl.dkaluza.chatserver.ports.out.MessageRepository;
import reactor.core.publisher.Flux;

class MessageService implements SendMessageUseCase {
    private final MessageRepository messageRepository;
    private final MessageBroker messageBroker;

    public MessageService(MessageRepository messageRepository, MessageBroker messageBroker) {
        this.messageRepository = messageRepository;
        this.messageBroker = messageBroker;
    }

    @Override
    public void sendMessage(Flux<Message> message) {
        var savedMessage = messageRepository.insert(message);
        messageBroker.send(savedMessage);
    }
}
