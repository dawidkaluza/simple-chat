package pl.dkaluza.chatserver.domain.events;

import pl.dkaluza.chatserver.domain.Message;

public record NewMessageEvent(Message message) {
}
