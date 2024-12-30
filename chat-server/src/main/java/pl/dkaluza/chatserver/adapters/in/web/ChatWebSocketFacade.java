package pl.dkaluza.chatserver.adapters.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import pl.dkaluza.chatserver.domain.Message;
import pl.dkaluza.chatserver.domain.MessageBroker;
import pl.dkaluza.chatserver.ports.in.SendMessageUseCase;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

import java.time.Instant;

@Component
class ChatWebSocketFacade {
    private final ObjectMapper objectMapper;
    private final SendMessageUseCase sendMessageUseCase;
    private final MessageBroker messageBroker;

    public ChatWebSocketFacade(ObjectMapper objectMapper, SendMessageUseCase sendMessageUseCase, MessageBroker messageBroker) {
        this.objectMapper = objectMapper;
        this.sendMessageUseCase = sendMessageUseCase;
        this.messageBroker = messageBroker;
    }

    public void send(WebSocketSession session, Flux<WebSocketMessage> input) {
        sendMessageUseCase.sendMessage(input.map(message -> toDomain(session, message)));
    }

    public Flux<WebSocketMessage> receive(WebSocketSession session) {
        return messageBroker.receive().map(message -> session.textMessage(toOutboundMessage(session, message)));
    }

    private Message toDomain(WebSocketSession wsSession, WebSocketMessage wsMessage) {
        var sender = toSender(wsSession);
        var message = toInboundMessage(wsMessage);
        var sentAt = Instant.now();
        return Message.of(sender, message, sentAt);
    }

    private String toSender(WebSocketSession wsSession) {
        var sender = wsSession.getHandshakeInfo().getHeaders().getFirst("X-Nickname");
        return sender == null ? "Anonymous" : sender;
    }

    private String toInboundMessage(WebSocketMessage wsMessage) {
        try {
            var payload = objectMapper.readValue(wsMessage.getPayloadAsText(), InboundMessage.class);
            return payload.message();
        } catch (JsonProcessingException e) {
            throw Exceptions.propagate(e);
        }
    }

    private String toOutboundMessage(WebSocketSession wsSession, Message message) {
        try {
            var outgoingMessage = new OutboundMessage(message.sender(), message.message());
            return objectMapper.writeValueAsString(outgoingMessage);
        } catch (JsonProcessingException e) {
            throw Exceptions.propagate(e);
        }
    }
}
