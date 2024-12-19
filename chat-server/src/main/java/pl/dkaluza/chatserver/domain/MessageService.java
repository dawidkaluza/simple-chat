package pl.dkaluza.chatserver.domain;

import org.reactivestreams.Publisher;
import pl.dkaluza.chatserver.ports.in.SendMessageUseCase;
import pl.dkaluza.chatserver.ports.out.MessageSender;
import pl.dkaluza.chatserver.ports.out.MessageRepository;

class MessageService implements SendMessageUseCase {
    private final MessageRepository messageRepository;
    private final MessageSender messageSender;

    public MessageService(MessageRepository messageRepository, MessageSender messageSender) {
        this.messageRepository = messageRepository;
        this.messageSender = messageSender;
    }

    @Override
    public void sendMessage(Publisher<Message> message) {
        var savedMessage = messageRepository.insert(message);
        messageSender.send(savedMessage);
    }
}
